package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.NotJoinedMatchException;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.model.Outcome.LOSE;
import static org.antop.billiardslove.model.Outcome.WIN;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchApi.class)
class MatchResultApiTest extends WebMvcBase {
    @MockBean
    private MatchService matchService;

    @DisplayName("정상적인 결과 입력")
    @Test
    void enter() throws Exception {
        // given
        when(matchService.enter(anyLong(), anyLong(), any())).then(invocation ->
                MatchDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .left(MatchPlayerDto.builder()
                                .id(10)
                                .number(10)
                                .nickname("안탑")
                                .handicap(22)
                                .rank(10)
                                .score(14)
                                .variation(2)
                                .result(Arrays.stream(invocation.getArgument(2, Outcome[].class))
                                        .map(Enum::name).
                                        toArray(String[]::new))
                                .build())
                        .right(MatchPlayerDto.builder()
                                .id(123)
                                .nickname("상대선수 닉네임")
                                .handicap(18)
                                .number(10)
                                .rank(15)
                                .score(452)
                                .variation(1)
                                .result(new String[]{"NONE", "NONE", "NONE"})
                                .build())
                        .closed(false)
                        .build()
        );
        long matchId = 7L;
        Outcome[] result = {WIN, WIN, LOSE};
        MatchApi.MatchResultEnterRequest request = new MatchApi.MatchResultEnterRequest(result);
        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/match/{id}", matchId)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("match-result-enter",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        requestFields(
                                fieldWithPath(MatchApi.MatchResultEnterRequest.Fields.RESULT)
                                        .description("선수가 입력한 자신의 경기 결과")
                                        .type(JsonFieldType.ARRAY)
                        ),
                        responseFields(RestDocsUtils.FieldSnippet.match())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(matchId)))
                .andExpect(jsonPath("$.left.result", is(Arrays.asList("WIN", "WIN", "LOSE"))))
        ;
    }

    @DisplayName("경기 결과의 개수가 1개인 경우")
    @Test
    void resultIsOne() throws Exception {
        // request
        Outcome[] result = {WIN};
        MatchApi.MatchResultEnterRequest request = new MatchApi.MatchResultEnterRequest(result);
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 7)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("match-result-enter-bad-request"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("경기 결과의 크기는 3개입니다.")))
        ;
    }

    @DisplayName("경기 결과의 개수가 4개인 경우")
    @Test
    void resultIsFour() throws Exception {
        // request
        Outcome[] result = {WIN, WIN, WIN, WIN};
        MatchApi.MatchResultEnterRequest request = new MatchApi.MatchResultEnterRequest(result);
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 7)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("match-result-enter-bad-request"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("경기 결과의 크기는 3개입니다.")))
        ;
    }

    @DisplayName("잘못된 결과 문자열을 입력했을 경우")
    @Test
    void invalidCode() throws Exception {
        // request
        String[] result = {"?", "WIN", "LOSE"};
        Map<String, String[]> request = Collections.singletonMap("result", result);
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 7)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("match-result-enter-bad-request"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("잘못된 포멧입니다.")))
        ;
    }

    @DisplayName("내가 속하지 않은 경기에 결과를 입력하려는 경우")
    @Test
    void notJoinedMatch() throws Exception {
        // when
        when(matchService.enter(anyLong(), anyLong(), any())).thenThrow(new NotJoinedMatchException());
        // request
        Outcome[] result = {WIN, WIN, LOSE};
        MatchApi.MatchResultEnterRequest request = new MatchApi.MatchResultEnterRequest(result);
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 32)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                .andDo(print())
                .andDo(RestDocsUtils.error("match-result-enter-not-joined"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("참여하지 않은 경기입니다.")))
        ;
    }

    @DisplayName("없는 경기")
    @Test
    void notFoundMatch() throws Exception {
        // when
        when(matchService.enter(anyLong(), anyLong(), any())).thenThrow(new MatchNotFoundException());
        // request
        Outcome[] result = {WIN, WIN, LOSE};
        MatchApi.MatchResultEnterRequest request = new MatchApi.MatchResultEnterRequest(result);
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 81)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                .andDo(print())
                .andDo(RestDocsUtils.error("error-match-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("경기를 찾을 수 없습니다.")))
        ;
    }

}

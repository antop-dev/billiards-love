package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.NotJoinedMatchException;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.util.Arrays;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchApi.class)
class MatchResultApiTest extends WebMvcBase {
    @MockBean
    private MatchService matchService;

    @Test
    void enter() throws Exception {
        // when
        when(matchService.enter(anyLong(), anyLong(), any())).then(invocation ->
                MatchDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .result(Arrays.stream(invocation.getArgument(2, Outcome[].class))
                                .map(Enum::name).
                                toArray(String[]::new))
                        .closed(false)
                        .opponent(PlayerDto.builder()
                                .id(123)
                                .nickname("상대선수 닉네임")
                                .handicap(18)
                                .number(10)
                                .rank(15)
                                .score(452)
                                .build())
                        .build()
        );
        // request
        String[] request = new String[]{"WIN", "WIN", "LOSE"};
        // action
        mockMvc.perform(put("/api/v1/match/{id}", 7)
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
                        PayloadDocumentation.requestBody(),
                        responseFields(RestDocsUtils.FieldSnippet.match())
                ))
                // verify
                .andExpect(status().isOk())
        ;
    }

    @Test
    void notJoinedMatch() throws Exception {
        // when
        when(matchService.enter(anyLong(), anyLong(), any())).thenThrow(new NotJoinedMatchException());
        // request
        String[] request = new String[]{"WIN", "WIN", "LOSE"};
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

    @Test
    void notFoundMatch() throws Exception {
        // when
        when(matchService.enter(anyLong(), anyLong(), any())).thenThrow(new MatchNotFoundException());
        // request
        String[] request = new String[]{"WIN", "WIN", "LOSE"};
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

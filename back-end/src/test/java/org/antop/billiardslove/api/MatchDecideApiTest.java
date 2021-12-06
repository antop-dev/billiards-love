package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.api.MatchApi.MatchDecideRequest;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.model.MatchResult;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchApi.class)
class MatchDecideApiTest extends WebMvcBase {
    @MockBean
    private MatchService matchService;

    @DisplayName("관리자가 아닌 유저로 접근 시 403")
    @Test
    void postUser() throws Exception {
        // given
        MatchDecideRequest request = MatchDecideRequest.builder()
                .left(new Outcome[]{Outcome.WIN, Outcome.WIN, Outcome.WIN})
                .right(new Outcome[]{Outcome.LOSE, Outcome.LOSE, Outcome.LOSE})
                .build();
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/match/{id}/decide", 1L)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                .andDo(print());
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("권한이 없습니다.")))
        ;
    }

    @DisplayName("관리자가 경기를 확정한다.")
    @Test
    void postManager() throws Exception {
        // given
        long matchId = 1L;
        when(matchService.decide(anyLong(), anyLong(), any(Outcome[].class), any(Outcome[].class)))
                .thenAnswer(inv -> MatchDto.builder()
                        .id(inv.getArgument(0))
                        .left(MatchPlayerDto.builder()
                                .id(100)
                                .number(72)
                                .nickname("왼쪽 선수 닉네임")
                                .handicap(23)
                                .rank(12)
                                .score(112)
                                .variation(2)
                                .progress(87.5)
                                .result(MatchResult.of(inv.getArgument(2, Outcome[].class)).toArrayString())
                                .build())
                        .right(MatchPlayerDto.builder()
                                .id(103)
                                .number(78)
                                .nickname("오른쪽 선수 닉네임")
                                .handicap(21)
                                .rank(56)
                                .score(100)
                                .variation(0)
                                .progress(70)
                                .result(MatchResult.of(inv.getArgument(3, Outcome[].class)).toArrayString())
                                .build())
                        .closed(true)
                        .build());
        MatchDecideRequest request = MatchDecideRequest.builder()
                .left(new Outcome[]{Outcome.WIN, Outcome.WIN, Outcome.WIN})
                .right(new Outcome[]{Outcome.LOSE, Outcome.LOSE, Outcome.LOSE})
                .build();
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/match/{id}/decide", matchId)
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                .andDo(print())
                .andDo(document("match-decide",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.matchId()),
                        requestFields(
                                fieldWithPath(MatchDecideRequest.Fields.LEFT).description("왼쪽 선수 경기 결과"),
                                fieldWithPath(MatchDecideRequest.Fields.RIGHT).description("오른쪽 선수 경기 결과")
                        ),
                        responseFields(RestDocsUtils.FieldSnippet.match())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(matchId)))
                .andExpect(jsonPath("$.left.result", is(Arrays.asList("WIN", "WIN", "WIN"))))
                .andExpect(jsonPath("$.right.result", is(Arrays.asList("LOSE", "LOSE", "LOSE"))))
                .andExpect(jsonPath("$.closed", is(true)))
        ;
    }
}

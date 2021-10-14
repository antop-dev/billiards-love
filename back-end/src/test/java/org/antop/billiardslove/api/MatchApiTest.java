package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.model.MatchResult;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.hamcrest.Matchers;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MatchApi.class)
class MatchApiTest extends WebMvcBase {
    @MockBean
    private MatchService matchService;

    @Test
    void matches() throws Exception {
        // when
        when(matchService.getMatches(anyLong(), anyLong())).then(invocation -> Arrays.asList(
                MatchDto.builder()
                        .id(1)
                        .result(MatchResult.of(Outcome.WIN, Outcome.LOSE, Outcome.WIN).toArrayString())
                        .closed(true)
                        .opponent(PlayerDto.builder()
                                .id(111)
                                .nickname("띠용")
                                .handicap(21)
                                .number(3)
                                .rank(1)
                                .variation(3)
                                .score(17)
                                .build())
                        .build(),
                MatchDto.builder()
                        .id(2)
                        .result(MatchResult.of(Outcome.LOSE, Outcome.LOSE, Outcome.LOSE).toArrayString())
                        .closed(false)
                        .opponent(PlayerDto.builder()
                                .id(222)
                                .nickname("스터디2")
                                .handicap(22)
                                .number(4)
                                .rank(3)
                                .score(-2)
                                .score(11)
                                .build())
                        .build(),
                MatchDto.builder()
                        .id(3)
                        .result(MatchResult.of(Outcome.NONE, Outcome.NONE, Outcome.NONE).toArrayString())
                        .closed(false)
                        .opponent(PlayerDto.builder()
                                .id(333)
                                .nickname("인디")
                                .handicap(25)
                                .number(5)
                                .rank(8)
                                .score(-3)
                                .build())
                        .build()
        ));

        mockMvc.perform(get("/api/v1/contest/{id}/matches", 123)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // document
                .andDo(document("matches",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.matches())
                ))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].result", is(Arrays.asList("WIN", "LOSE", "WIN"))))
                .andExpect(jsonPath("$[0].closed", is(true)))
                .andExpect(jsonPath("$[1].result", is(Arrays.asList("LOSE", "LOSE", "LOSE"))))
                .andExpect(jsonPath("$[1].closed", is(false)))
                .andExpect(jsonPath("$[2].result", is(Arrays.asList("NONE", "NONE", "NONE"))))
                .andExpect(jsonPath("$[2].closed", is(false)))
        ;

    }

    @Test
    void match() throws Exception {
        long matchId = 991;
        // when
        when(matchService.getMatch(anyLong(), anyLong())).then(invocation ->
                Optional.of(MatchDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .result(MatchResult.of(Outcome.WIN, Outcome.WIN, Outcome.WIN).toArrayString())
                        .closed(true)
                        .opponent(PlayerDto.builder()
                                .id(9)
                                .nickname("안탑")
                                .handicap(22)
                                .number(15)
                                .rank(8)
                                .variation(-4)
                                .score(115)
                                .build())
                        .build())
        );
        // action
        mockMvc.perform(get("/api/v1/match/{id}", matchId)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(document("match",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.matchId()),
                        responseFields(RestDocsUtils.FieldSnippet.match())
                ))
                // verify
                .andExpect(jsonPath("$.id", NumberMatcher.is(matchId)))
                .andExpect(jsonPath("$.opponent.id", is(9)))
                .andExpect(jsonPath("$.opponent.nickname", is("안탑")))
                .andExpect(jsonPath("$.opponent.handicap", is(22)))
                .andExpect(jsonPath("$.opponent.number", is(15)))
                .andExpect(jsonPath("$.opponent.rank", is(8)))
                .andExpect(jsonPath("$.opponent.score", is(115)))
                .andExpect(jsonPath("$.opponent.variation", is(-4)))
                .andExpect(jsonPath("$.result", Matchers.is(Arrays.asList("WIN", "WIN", "WIN"))))
                .andExpect(jsonPath("$.closed", is(true)))
        ;
    }

}

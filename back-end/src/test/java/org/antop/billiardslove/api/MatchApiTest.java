package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.service.MatchService;
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
    @MockBean
    private MatchDao matchDao;

    @Test
    void matches() throws Exception {
        final MatchPlayerDto me = MatchPlayerDto.builder()
                .id(10)
                .number(10)
                .nickname("안탑")
                .handicap(22)
                .rank(10)
                .score(14)
                .variation(2)
                .result(new String[]{"WIN", "WIN", "WIN"})
                .build();
        // when
        when(matchService.getMatches(anyLong(), anyLong())).then(invocation -> Arrays.asList(
                MatchDto.builder()
                        .id(1)
                        .left(me)
                        .right(MatchPlayerDto.builder()
                                .id(11)
                                .number(11)
                                .nickname("띠용")
                                .handicap(18)
                                .rank(21)
                                .score(7)
                                .variation(-1)
                                .result(new String[]{"LOSE", "LOSE", "LOSE"})
                                .build())
                        .closed(true)
                        .build(),
                MatchDto.builder()
                        .id(2)
                        .left(me)
                        .right(MatchPlayerDto.builder()
                                .id(12)
                                .number(12)
                                .nickname("인디")
                                .handicap(20)
                                .rank(19)
                                .score(11)
                                .variation(-2)
                                .result(new String[]{"NONE", "NONE", "NONE"})
                                .build())
                        .closed(false)
                        .build(),
                MatchDto.builder()
                        .id(3)
                        .left(me)
                        .right(MatchPlayerDto.builder()
                                .id(33)
                                .number(13)
                                .nickname("스터디2")
                                .handicap(30)
                                .rank(2)
                                .score(30)
                                .variation(-1)
                                .result(new String[]{"ABSTENTION", "ABSTENTION", "ABSTENTION"})
                                .build())
                        .closed(false)
                        .build()
        ));

        mockMvc.perform(get("/api/v1/contest/{contestId}/matches", 123)
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
                .andExpect(jsonPath("$[0].left.id", is(10)))
                .andExpect(jsonPath("$[0].left.number", is(10)))
                .andExpect(jsonPath("$[0].left.nickname", is("안탑")))
                .andExpect(jsonPath("$[0].left.result", is(Arrays.asList("WIN", "WIN", "WIN"))))
                .andExpect(jsonPath("$[0].right.id", is(11)))
                .andExpect(jsonPath("$[0].right.number", is(11)))
                .andExpect(jsonPath("$[0].right.nickname", is("띠용")))
                .andExpect(jsonPath("$[0].right.handicap", is(18)))
                .andExpect(jsonPath("$[0].right.rank", is(21)))
                .andExpect(jsonPath("$[0].right.score", is(7)))
                .andExpect(jsonPath("$[0 ].right.variation", is(-1)))
                .andExpect(jsonPath("$[0].right.result", is(Arrays.asList("LOSE", "LOSE", "LOSE"))))
                .andExpect(jsonPath("$[0].closed", is(true)))
                .andExpect(jsonPath("$[1].right.id", is(12)))
                .andExpect(jsonPath("$[1].right.number", is(12)))
                .andExpect(jsonPath("$[1].right.nickname", is("인디")))
                .andExpect(jsonPath("$[1].right.handicap", is(20)))
                .andExpect(jsonPath("$[1].right.rank", is(19)))
                .andExpect(jsonPath("$[1].right.score", is(11)))
                .andExpect(jsonPath("$[1].right.variation", is(-2)))
                .andExpect(jsonPath("$[1].right.result", is(Arrays.asList("NONE", "NONE", "NONE"))))
                .andExpect(jsonPath("$[1].closed", is(false)))
                .andExpect(jsonPath("$[2].right.id", is(33)))
                .andExpect(jsonPath("$[2].right.number", is(13)))
                .andExpect(jsonPath("$[2].right.nickname", is("스터디2")))
                .andExpect(jsonPath("$[2].right.handicap", is(30)))
                .andExpect(jsonPath("$[2].right.rank", is(2)))
                .andExpect(jsonPath("$[2].right.score", is(30)))
                .andExpect(jsonPath("$[2].right.variation", is(-1)))
                .andExpect(jsonPath("$[2].right.result", is(Arrays.asList("ABSTENTION", "ABSTENTION", "ABSTENTION"))))
                .andExpect(jsonPath("$[2].closed", is(false)))
        ;

    }

    @Test
    void match() throws Exception {
        long matchId = 1;
        // when
        when(matchService.getMatch(anyLong(), anyLong())).then(invocation ->
                Optional.of(MatchDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .left(MatchPlayerDto.builder()
                                .id(10)
                                .number(10)
                                .nickname("안탑")
                                .handicap(22)
                                .rank(10)
                                .score(14)
                                .variation(2)
                                .result(new String[]{"WIN", "WIN", "WIN"})
                                .build())
                        .right(MatchPlayerDto.builder()
                                .id(11)
                                .number(11)
                                .nickname("띠용")
                                .handicap(18)
                                .rank(21)
                                .score(7)
                                .variation(-1)
                                .result(new String[]{"LOSE", "LOSE", "LOSE"})
                                .build())
                        .closed(true)
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
                .andExpect(jsonPath("$.left.id", is(10)))
                .andExpect(jsonPath("$.left.number", is(10)))
                .andExpect(jsonPath("$.left.nickname", is("안탑")))
                .andExpect(jsonPath("$.left.handicap", is(22)))
                .andExpect(jsonPath("$.left.rank", is(10)))
                .andExpect(jsonPath("$.left.score", is(14)))
                .andExpect(jsonPath("$.left.variation", is(2)))
                .andExpect(jsonPath("$.left.result", is(Arrays.asList("WIN", "WIN", "WIN"))))
                .andExpect(jsonPath("$.right.id", is(11)))
                .andExpect(jsonPath("$.right.number", is(11)))
                .andExpect(jsonPath("$.right.nickname", is("띠용")))
                .andExpect(jsonPath("$.right.handicap", is(18)))
                .andExpect(jsonPath("$.right.rank", is(21)))
                .andExpect(jsonPath("$.right.score", is(7)))
                .andExpect(jsonPath("$.right.variation", is(-1)))
                .andExpect(jsonPath("$.right.result", is(Arrays.asList("LOSE", "LOSE", "LOSE"))))
                .andExpect(jsonPath("$.closed", is(true)))
        ;
    }

}

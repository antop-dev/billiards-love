package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.service.PlayerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContestRankApi.class)
class ContestRankApiTest extends WebMvcBase {
    @MockBean
    private PlayerService playerService;

    @DisplayName("순위표를 조회한다.")
    @Test
    void rankApi() throws Exception {
        /*
        랭크, 선수번호 순으로 오름차순 정렬 되어야 한다.
         */
        // when
        when(playerService.getPlayers(anyLong())).thenReturn(Arrays.asList(
                PlayerDto.builder().id(1).nickname("안탑").rank(3).number(5).handicap(30).build(),
                PlayerDto.builder().id(2).nickname("스터디2").rank(1).number(10).handicap(22).build(),
                PlayerDto.builder().id(3).nickname("띠용").rank(2).number(21).handicap(25).build(),
                PlayerDto.builder().id(4).nickname("지수.나").rank(2).number(20).handicap(24).build()
        ));
        // action
        mockMvc.perform(
                        get("/api/v1/contest/{id}/ranks", 1)
                                .header(HttpHeaders.AUTHORIZATION, userToken())
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("ranks",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.players())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].nickname", is("스터디2")))
                .andExpect(jsonPath("$[0].handicap", is(22)))
                .andExpect(jsonPath("$[0].number", is(10)))
                .andExpect(jsonPath("$[0].rank", is(1)))

                .andExpect(jsonPath("$[1].id", is(4)))
                .andExpect(jsonPath("$[1].nickname", is("지수.나")))
                .andExpect(jsonPath("$[1].handicap", is(24)))
                .andExpect(jsonPath("$[1].number", is(20)))
                .andExpect(jsonPath("$[1].rank", is(2)))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].nickname", is("띠용")))
                .andExpect(jsonPath("$[2].handicap", is(25)))
                .andExpect(jsonPath("$[2].number", is(21)))
                .andExpect(jsonPath("$[2].rank", is(2)))

                .andExpect(jsonPath("$[3].id", is(1)))
                .andExpect(jsonPath("$[3].nickname", is("안탑")))
                .andExpect(jsonPath("$[3].handicap", is(30)))
                .andExpect(jsonPath("$[3].number", is(5)))
                .andExpect(jsonPath("$[3].rank", is(3)))
        ;

    }
}

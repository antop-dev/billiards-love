package org.antop.billiardslove.integration;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.TestSecurityConfig;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.model.ContestState;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@DisplayName("대회 조회 테스트")
class ContestInfoTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("대회 1건 상세 조회")
    @Test
    void getContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L); // 관리자 토큰
        // when
        ResultActions actions = request(token, 1L)
                .andDo(document("contest-get",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contestWithPlayer())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(1L)))
                .andExpect(jsonPath("$.title", is("2021 리그전")))
                .andExpect(jsonPath("$.description", is("2021.01.01~")))
                .andExpect(jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(jsonPath("$.endDate", is("2021-12-30")))
                .andExpect(jsonPath("$.maxJoiner", is(32)))
                .andExpect(jsonPath("$.currentJoiner", is(5)))
                .andExpect(jsonPath("$.stateCode", is(ContestState.PROCEEDING.name())))
                .andExpect(jsonPath("$.stateName", is("진행중")))
                .andExpect(jsonPath("$.player").exists()) // 회원1은 대회1에 참여중
        ;
    }

    @DisplayName("존재하지 않는 대회 조회")
    @Test
    void contestNotFound() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(4L);
        // when
        ResultActions actions = request(token, 99999999999L)
                .andDo(RestDocsUtils.error("contest-get-not-found"));
        // then
        actions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("대회를 찾을 수 없습니다.")))
        ;
    }

    private ResultActions request(String token, long contestId) throws Exception {
        return mockMvc.perform(
                        get("/api/v1/contest/{contestId}", contestId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
    }

}

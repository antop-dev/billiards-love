package org.antop.billiardslove.integration;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.TestSecurityConfig;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.model.ContestState;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@DisplayName("대회 중지 테스트")
class ContestStopTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("관리자가 대회 중지")
    @Test
    void openContestByManager() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L); // 관리자 토큰
        // when
        ResultActions actions = request(token, 3L)
                .andDo(document("contest-stop",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.STOPPED.name())))
                .andExpect(jsonPath("$.stateName", is("중지")))
        ;
    }

    @DisplayName("일반 유저로 대회 중지")
    @Test
    void openContestByMember() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(2L); // 일반 회원 토큰
        // when
        ResultActions actions = request(token, 3L)
                .andDo(RestDocsUtils.error("contest-stop-not-manager"));
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("권한이 없습니다.")))
        ;
    }

    @DisplayName("진행중인 대회 중지")
    @Test
    void openProceedingContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 1L);
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is("STOPPED")))
                .andExpect(jsonPath("$.stateName", is("중지")))
        ;
    }

    @DisplayName("중지된 대회 중지")
    @Test
    void openStoppedContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 5L);
        // then
        // 다시 중지해도 괜찮다.
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is("STOPPED")))
                .andExpect(jsonPath("$.stateName", is("중지")))
        ;
    }

    @DisplayName("종료된 대회 중지")
    @Test
    void openEndContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 6L)
                .andDo(RestDocsUtils.error("contest-stop-end"));
        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("종료된 대회입니다.")))
        ;
    }

    private ResultActions request(String token, long contestId) throws Exception {
        return mockMvc.perform(
                        post("/api/v1/contest/{contestId}/stop", contestId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
    }

}

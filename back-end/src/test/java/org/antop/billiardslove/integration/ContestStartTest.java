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
@DisplayName("대회 시작 테스트")
class ContestStartTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("관리자가 대회 시작")
    @Test
    void startContestByManager() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L); // 관리자 토큰
        // when
        ResultActions actions = request(token, 2L)  // 접수중인 대회
                .andDo(document("contest-start",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.PROCEEDING.name())))
                .andExpect(jsonPath("$.stateName", is("진행중")))
        ;
    }

    @DisplayName("일반 유저로 대회 시작")
    @Test
    void openContestByMember() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(2L); // 일반 회원 토큰
        // when
        ResultActions actions = request(token, 2L)
                .andDo(RestDocsUtils.error("contest-start-not-manager"));
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("권한이 없습니다.")))
        ;
    }

    @DisplayName("진행중인 대회 시작")
    @Test
    void startProceedingContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 1L) // 진행중인 대회
                .andDo(RestDocsUtils.error("contest-start-proceeding"));
        // then
        actions.andExpect(status().isBadRequest())
                // 이미 시작한 대회는 다시 시작
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("진행중인 대회입니다.")))
        ;
    }

    @DisplayName("준비중인 대회 시작")
    @Test
    void startPreparingContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 3L) // 준비중인 대회
                .andDo(RestDocsUtils.error("contest-start-preparing"));
        // then
        actions.andExpect(status().isBadRequest())
                // 준비중인 대회는 시작할 수 없다.
                // 준비 -> 접수 -> 시작 과정임.
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("준비중인 대회입니다.")))
        ;
    }

    @DisplayName("중지된 대회 시작")
    @Test
    void startStoppedContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 5L);
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.PROCEEDING.name())))
                .andExpect(jsonPath("$.stateName", is("진행중")))
        ;
    }

    @DisplayName("종료된 대회 시작")
    @Test
    void startEndContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 6L)
                .andDo(RestDocsUtils.error("contest-start-end"));
        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("종료된 대회입니다.")))
        ;
    }

    private ResultActions request(String token, long contestId) throws Exception {
        return mockMvc.perform(
                        post("/api/v1/contest/{contestId}/start", contestId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
    }

}

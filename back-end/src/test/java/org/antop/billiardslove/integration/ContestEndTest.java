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
@DisplayName("대회 종료 테스트")
class ContestEndTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("관리자가 대회 종료")
    @Test
    void openContestByManager() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L); // 관리자 토큰
        // when
        ResultActions actions = request(token, 5L) // 중지된 대회
                .andDo(document("contest-end",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.END.name())))
                .andExpect(jsonPath("$.stateName", is("종료")))
        ;
    }

    @DisplayName("일반 유저로 대회 종료")
    @Test
    void openContestByMember() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(2L); // 일반 회원 토큰
        // when
        ResultActions actions = request(token, 5L)
                .andDo(RestDocsUtils.error("contest-end-not-manager"));
        // then
        actions.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("권한이 없습니다.")))
        ;
    }

    @DisplayName("접수중인 대회 종료")
    @Test
    void openAcceptingContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 2L)
                .andDo(RestDocsUtils.error("contest-end-accept"));
        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("접수중인 대회입니다.")))
        ;
    }

    @DisplayName("진행중인 대회 종료")
    @Test
    void openProceedingContest() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L);
        // when
        ResultActions actions = request(token, 1L)
                .andDo(RestDocsUtils.error("contest-end-proceeding"));
        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("진행중인 대회입니다.")))
        ;
    }

    private ResultActions request(String token, long contestId) throws Exception {
        return mockMvc.perform(
                        post("/api/v1/contest/{contestId}/end", contestId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
    }

}

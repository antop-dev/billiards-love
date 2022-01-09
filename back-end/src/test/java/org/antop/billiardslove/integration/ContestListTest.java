package org.antop.billiardslove.integration;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("대회 목록 조회 테스트")
class ContestListTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("회원 대회 목록 조회")
    @Test
    void findForMember() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(3L); // 회원
        // when
        ResultActions actions = request(token)
                .andDo(document("contest-list",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        responseFields(RestDocsUtils.FieldSnippet.contestsWithPlayer())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].player").doesNotExist())
                .andExpect(jsonPath("$[1].player").doesNotExist())
                .andExpect(jsonPath("$[2].player").exists())
                .andExpect(jsonPath("$[3].player").exists())
        ;
    }

    @DisplayName("관리자가 대회 목록 조회")
    @Test
    void findForManager() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(1L); // 관리자
        // when
        ResultActions actions = request(token)
                .andDo(document("contest-list",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        responseFields(RestDocsUtils.FieldSnippet.contestsWithPlayer())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[5].player").exists())
                .andExpect(jsonPath("$[6].player").exists())
        ;
    }

    private ResultActions request(String token) throws Exception {
        return mockMvc.perform(
                        get("/api/v1/contests")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());
    }
}

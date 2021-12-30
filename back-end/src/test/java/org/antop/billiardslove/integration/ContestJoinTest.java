package org.antop.billiardslove.integration;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.api.ContestJoiningApi.JoiningRequest;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import util.JsonUtils;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.api.ContestJoiningApi.JoiningRequest.Fields.HANDICAP;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("대회 참가 테스트")
class ContestJoinTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("대회에 참가")
    @Test
    void join() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(5L); // 춘향이
        int handicap = 30;
        JoiningRequest request = new JoiningRequest(handicap);
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/contest/{id}/join", 2L)
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andDo(document("contest-join",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        requestFields(fieldWithPath(HANDICAP).description("참가 핸디캡")),
                        responseFields(RestDocsUtils.FieldSnippet.contestWithPlayer())
                ));
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.currentJoiner", is(5))) // 4 → 5
                .andExpect(jsonPath("$.stateCode", is(ContestState.ACCEPTING.name())))
                .andExpect(jsonPath("$.stateName", is("접수중")))
                .andExpect(jsonPath("$.player").exists())
                .andExpect(jsonPath("$.player.id").exists())
                .andExpect(jsonPath("$.player.nickname", is("춘향이")))
                .andExpect(jsonPath("$.player.handicap", is(handicap)))
                .andExpect(jsonPath("$.player.score", is(0)))
                .andExpect(jsonPath("$.player.variation", is(0)))
        ;
    }

    @DisplayName("핸디를 입력하지 않음")
    @Test
    void joinRequiredHandicap() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(5L); // 춘향이

        JoiningRequest request = new JoiningRequest(null);
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/contest/{id}/join", 2L)
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andDo(RestDocsUtils.error("contest-join-required-handicap"));
        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("참가 핸디를 입력하세요.")))
        ;
    }

    // FIXME: 상태별로 세분화된 메세지를 주자.
    @DisplayName("참가할 수 없는 상태의 대회에 참가")
    @Test
    void canNotJoin() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(5L); // 춘향이
        JoiningRequest request = new JoiningRequest(30);
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/contest/{id}/join", 1L)
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andDo(RestDocsUtils.error("contest-join-can-not"));

        // verify
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("대회에 참가할 수 없는 상태입니다.")))
        ;
    }

    @DisplayName("이미 참가한 대회에 다시 참가")
    @Test
    void alreadyJoin() throws Exception {
        // given
        String token = jwtTokenProvider.createToken(3L); // 이미 대회에 참가 되어있는 회원
        JoiningRequest request = new JoiningRequest(30);
        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/contest/{id}/join", 2L)
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andDo(RestDocsUtils.error("contest-join-already"));
        // verify
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("이미 참가한 대회입니다.")))

        ;
    }

}

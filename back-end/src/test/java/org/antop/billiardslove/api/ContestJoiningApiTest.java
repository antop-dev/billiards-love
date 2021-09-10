package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.RestDocsUtils.Attributes;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Collections;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.api.ContestJoiningApi.JoiningRequest.Fields.HANDICAP;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestJoiningApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ContestService contestService;

    /**
     * 정상 참가
     */
    @Test
    void join() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("5");
        ContestJoiningApi.JoiningRequest request = new ContestJoiningApi.JoiningRequest(30);
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 2)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-join",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id")
                                .description("대회 아이디")
                                .attributes(Attributes.type(JsonFieldType.NUMBER))
                        ),
                        requestFields(
                                fieldWithPath(HANDICAP).description("참가 핸디캡").optional()
                        )
                ))
                // verify
                .andExpect(status().isOk())
        ;
    }

    /**
     * 참가할 수 없는 상태의 대회에 참가한다.
     */
    @Test
    void canNotJoining() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 1)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-cant-join"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("대회에 참가할 수 없는 상태입니다.")))

        ;
    }

    /**
     * 이미 참가한 상태에 다시 참가한다.
     */
    @Test
    void alreadyJoining() throws Exception {
        /* 4번 회원은 2번 대회에 이미 참가한 상태이다. */
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 2)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-join"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("이미 참가한 대회입니다.")))

        ;
    }

    /**
     * 참가 취소
     */
    @Test
    void cancelJoining() throws Exception {
        String token = jwtTokenProvider.createToken("4");
        mockMvc.perform(delete("/api/v1/contest/{id}/join", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andDo(document("contest-join-cancel",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id")
                                .description("대회 아이디")
                                .attributes(Attributes.type(JsonFieldType.NUMBER))
                        )
                ))
                .andExpect(status().isOk())
        ;

        // TODO(안정용): 테스트 완성
//        Contest contest = contestService.getContest(2);
//        assertThat(contest.getPlayers(), hasSize(3)); // 4 -> 3
    }

}

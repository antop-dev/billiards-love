package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.type;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestStateApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void open() throws Exception {
        long contestId = 3;
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/open", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-open",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER))),
                        responseFields(RestDocsUtils.FieldsSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.ACCEPTING.getCode())))
                .andExpect(jsonPath("$.stateName", is(ContestState.ACCEPTING.name())))
        ;
    }

    @Test
    void start() throws Exception {
        long contestId = 2; // 접수중인 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/start", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-start",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER))),
                        responseFields(RestDocsUtils.FieldsSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.PROCEEDING.getCode())))
                .andExpect(jsonPath("$.stateName", is(ContestState.PROCEEDING.name())))
        ;
    }

    /**
     * 대회 상태가 접수중/중지인 대회에서만 시작이 가능하다.<br>
     */
    @Test
    void canNotStart() throws Exception {
        long contestId = 1; // 진행중인 대회
        String token = jwtTokenProvider.createToken("1");
        mockMvc.perform(post("/api/v1/contest/{id}/start", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-start-can-not"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("대회를 시작할 수 없는 상태입니다.")))
        ;
    }

    @Test
    void stop() throws Exception {
        long contestId = 1; // 진행중인 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/stop", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                // document
                .andDo(document("contest-stop",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER))),
                        responseFields(RestDocsUtils.FieldsSnippet.contest())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.STOPPED.getCode())))
                .andExpect(jsonPath("$.stateName", is(ContestState.STOPPED.name())))
        ;
    }

    @Test
    void canNotStop() throws Exception {
        long contestId = 2; // 접수중인 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/stop", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-stop-can-not"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("대회를 중지할 수 없는 상태입니다.")))
        ;
    }

    @Test
    void end() throws Exception {
        long contestId = 2; // 접수중인 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/end", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-end",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER))),
                        responseFields(RestDocsUtils.FieldsSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.END.getCode())))
                .andExpect(jsonPath("$.stateName", is(ContestState.END.name())))
        ;
    }

    @Test
    void alreadyEnd() throws Exception {
        long contestId = 6; // 종료된 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/{id}/end", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-end-already"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("이미 종료된 대회입니다.")))
        ;
    }

    @Test
    void contestNotFound() throws Exception {
        long contestId = 99; // 존재하지 않는 대회
        String token = jwtTokenProvider.createToken("1");
        mockMvc.perform(post("/api/v1/contest/{id}/start", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-contest-not-found"))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", is("대회를 찾을 수 없습니다.")))
        ;
    }

    @Test
    void unauthorized() throws Exception {
        // 인증 토큰을 보내지 않는다.
        mockMvc.perform(post("/api/v1/contest/{id}/start", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-unauthorized"))
                // verify
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(HttpStatus.UNAUTHORIZED.value())))
                .andExpect(jsonPath("$.message", is("인증 토큰이 유효하지 않습니다.")))
        ;
    }

}

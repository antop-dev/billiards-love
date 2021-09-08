package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.type;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestStateApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @SpyBean
    private ContestService contestService;

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
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER)))
                ))
                // verify
                .andExpect(status().isOk());
        // contestService.open() 를 한번만 호출 했는지 검사
        verify(contestService, times(1)).open(contestId);
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
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER)))
                ))
                // verify
                .andExpect(status().isOk());
        // contestService.open({contestId}) 를 한번만 호출 했는지 검사
        verify(contestService, times(1)).start(anyLong());
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
                .andDo(RestDocsUtils.error("error-contest-cant-start"))
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
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER)))
                ))
                .andExpect(status().isOk());
        // contestService.stop() 한번만 호출 했는지 검사
        verify(contestService, times(1)).stop(contestId);
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
                .andDo(RestDocsUtils.error("error-contest-cant-stop"))
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
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(type(NUMBER)))
                ))
                // verify
                .andExpect(status().isOk());
        // contestService.open({contestId}) 를 한번만 호출 했는지 검사
        verify(contestService, times(1)).end(contestId);
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
                .andDo(RestDocsUtils.error("error-contest-already-end"))
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
        long contestId = 2;
        // 인증 토큰을 보내지 않는다.
        mockMvc.perform(post("/api/v1/contest/{id}/start", contestId)
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

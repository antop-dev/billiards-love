package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestParticipationApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 정상 참가
     */
    @Test
    void participate() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("5");
        int handicap = 30;
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", handicap));
        // action
        mockMvc.perform(post("/api/v1/contest/2/participate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
        ;
    }

    /**
     * 참가할 수 없는 상태의 대회에 참가한다.
     */
    @Test
    void canNotParticipate() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/1/participate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
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
    void alreadyParticipate() throws Exception {
        /* 4번 회원은 2번 대회에 이미 참가한 상태이다. */
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/2/participate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("이미 참가한 대회입니다.")))

        ;
    }
}

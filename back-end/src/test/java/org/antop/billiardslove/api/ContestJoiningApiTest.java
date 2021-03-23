package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        int handicap = 30;
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", handicap));
        // action
        mockMvc.perform(post("/api/v1/contest/2/join")
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
    void canNotJoining() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/1/join")
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
    void alreadyJoining() throws Exception {
        /* 4번 회원은 2번 대회에 이미 참가한 상태이다. */
        // request
        String token = jwtTokenProvider.createToken("4");
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("handicap", 30));

        // action
        mockMvc.perform(post("/api/v1/contest/2/join")
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

    /**
     * 참가 취소
     */
    @Test
    void cancelJoining() throws Exception {
        String token = jwtTokenProvider.createToken("4");
        mockMvc.perform(delete("/api/v1/contest/2/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        Contest contest = contestService.getContest(2);
        assertThat(contest.getPlayers(), hasSize(3)); // 4 -> 3
    }

}

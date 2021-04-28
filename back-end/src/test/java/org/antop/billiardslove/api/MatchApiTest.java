package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class MatchApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void matches() throws Exception {
        // 대회 아이디
        long contestId = 1;
        // 회원 아이디
        long memberId = 2;
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("" + memberId);

        mockMvc.perform(get("/api/v1/contest/" + contestId + "/matches")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].opponent.id", is(1)))
                .andExpect(jsonPath("$[0].opponent.number", is(1)))
                .andExpect(jsonPath("$[0].opponent.nickname", is("안탑")))
                .andExpect(jsonPath("$[0].result[0]", is("LOSE")))
                .andExpect(jsonPath("$[0].result[1]", is("LOSE")))
                .andExpect(jsonPath("$[0].result[2]", is("WIN")))
                .andExpect(jsonPath("$[0].closed", is(true)))
                .andExpect(jsonPath("$[1].closed", is(false)))
        ;
    }

    @Test
    void match() throws Exception {
        // 경기 아이디
        long matchId = 5;
        // 회원 아이디
        long memberId = 3;
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("" + memberId);

        mockMvc.perform(get("/api/v1/match/" + matchId)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", NumberMatcher.is(matchId)))
                .andExpect(jsonPath("$.opponent.id", is(2)))
                .andExpect(jsonPath("$.opponent.number", is(2)))
                .andExpect(jsonPath("$.opponent.nickname", is("띠용")))
                .andExpect(jsonPath("$.result[0]", is("WIN")))
                .andExpect(jsonPath("$.result[1]", is("WIN")))
                .andExpect(jsonPath("$.result[2]", is("WIN")))
                .andExpect(jsonPath("$.closed", is(false)))
        ;
    }

}

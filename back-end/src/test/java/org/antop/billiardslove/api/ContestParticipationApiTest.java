package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestParticipationApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Test
    void normalResponseApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("4");
        int handicap = 30;

        String requestBody = "{\n" +
                "  \"handicap\": " + handicap + "\n" +
                "}";

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

    @Test
    void reParticipationApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("4");
        int handicap = 30;

        String requestBody = "{\n" +
                "  \"handicap\": " + handicap + "\n" +
                "}";
        // action
        mockMvc.perform(post("/api/v1/contest/1/participate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("참가할 수 없는 대회입니다.")))

        ;
    }

    @Test
    void participationApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");
        int handicap = 30;

        String requestBody = "{\n" +
                "  \"handicap\": " + handicap + "\n" +
                "}";
        // action
        mockMvc.perform(post("/api/v1/contest/2/participate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("이미 참가한 대회입니다.")))
        ;
    }
}

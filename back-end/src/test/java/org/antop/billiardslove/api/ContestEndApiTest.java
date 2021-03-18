package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestEndApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @SpyBean
    private ContestService contestService;

    @Test
    void end() throws Exception {
        long contestId = 2; // 접수중인 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/" + contestId + "/end")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk());
        // contestService.open({contestId}) 를 한번만 호출 했는지 검사
        verify(contestService, times(1)).end(contestId);
    }

    @Test
    void alreadyEnd() throws Exception {
        long contestId = 6; // 종료된 대회
        String token = jwtTokenProvider.createToken("1"); // 관리자
        mockMvc.perform(post("/api/v1/contest/" + contestId + "/end")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("이미 종료된 대회입니다.")))
        ;
    }

}

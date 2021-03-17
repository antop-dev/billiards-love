package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestModifyApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void modifyManager() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        String name = "2021 리그전 수정";
        String description = "리그전 대회 수정";
        String requestBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"start\": {\n" +
                "    \"startDate\": \"20210101\",\n" +
                "    \"startTime\": \"000000\"\n" +
                "  },\n" +
                "  \"end\": {\n" +
                "    \"endDate\": \"20211231\",\n" +
                "    \"endTime\": \"235959\"\n" +
                "  },\n" +
                "  \"maximumParticipants\": 64\n" +
                "}";

        // action
        mockMvc.perform(put("/api/v1/contest/5")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.start", notNullValue()))
                .andExpect(jsonPath("$.start.startDate", notNullValue()))
                .andExpect(jsonPath("$.start.startTime", notNullValue()))
                .andExpect(jsonPath("$.end", notNullValue()))
                .andExpect(jsonPath("$.end.endDate", notNullValue()))
                .andExpect(jsonPath("$.end.endTime", notNullValue()))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.code", notNullValue()))
                .andExpect(jsonPath("$.state.name", notNullValue()))
                .andExpect(jsonPath("$.maximumParticipants", is(64)))
        ;
    }

    @Test
    void modifyUser() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");

        String name = "2021 리그전 수정";
        String description = "리그전 대회 수정";
        String requestBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"start\": {\n" +
                "    \"startDate\": \"20210101\",\n" +
                "    \"startTime\": \"000000\"\n" +
                "  },\n" +
                "  \"end\": {\n" +
                "    \"endDate\": \"20211231\",\n" +
                "    \"endTime\": \"235959\"\n" +
                "  },\n" +
                "  \"maximumParticipants\": 64\n" +
                "}";

        // action
        mockMvc.perform(put("/api/v1/contest/5")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isForbidden()) // 403 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.FORBIDDEN.value())))
                .andExpect(jsonPath("$.message", is("권한이 없습니다.")))
        ;
    }

    @Test
    void alreadyContestEnd() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        String name = "2021 리그전 수정";
        String description = "리그전 대회 수정";
        String requestBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"start\": {\n" +
                "    \"startDate\": \"20210101\",\n" +
                "    \"startTime\": \"000000\"\n" +
                "  },\n" +
                "  \"end\": {\n" +
                "    \"endDate\": \"20211231\",\n" +
                "    \"endTime\": \"235959\"\n" +
                "  },\n" +
                "  \"maximumParticipants\": 64\n" +
                "}";

        // action
        mockMvc.perform(put("/api/v1/contest/6")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isInternalServerError()) // 500 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is("이미 종료된 대회입니다.")))
        ;
    }

    @Test
    void alreadyContestProgress() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        String name = "2021 리그전 수정";
        String description = "리그전 대회 수정";
        String requestBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"start\": {\n" +
                "    \"startDate\": \"20210101\",\n" +
                "    \"startTime\": \"000000\"\n" +
                "  },\n" +
                "  \"end\": {\n" +
                "    \"endDate\": \"20211231\",\n" +
                "    \"endTime\": \"235959\"\n" +
                "  },\n" +
                "  \"maximumParticipants\": 64\n" +
                "}";

        // action
        mockMvc.perform(put("/api/v1/contest/1")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isInternalServerError()) // 500 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is("이미 진행된 대회입니다.")))
        ;
    }
}

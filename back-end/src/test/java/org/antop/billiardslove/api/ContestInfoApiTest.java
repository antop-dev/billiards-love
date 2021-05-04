package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestInfoApiTest extends SpringBootBase {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void informationApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");
        // action
        mockMvc.perform(get("/api/v1/contest/1").header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("2021 리그전")))
                .andExpect(jsonPath("$.description", is("2021.01.01~")))
                .andExpect(jsonPath("$.start.date", is("2021-01-01")))
                .andExpect(jsonPath("$.start.time", is("00:00:00")))
                .andExpect(jsonPath("$.end.date", is("2021-12-30")))
                .andExpect(jsonPath("$.end.time", is("23:59:59")))
                .andExpect(jsonPath("$.state.code", is("0")))
                .andExpect(jsonPath("$.state.name", is("PROCEEDING")))
                .andExpect(jsonPath("$.maxJoiner", is(32)))
                .andExpect(jsonPath("$.joined", is(true)))
                .andExpect(jsonPath("$.player.id", is(2)))
                // 회원의 핸디탭은 20인데 참가는 24로 했다.
                .andExpect(jsonPath("$.player.id", is(2)))
                .andExpect(jsonPath("$.player.number", is(2)))
                .andExpect(jsonPath("$.player.nickname", is("띠용")))
                .andExpect(jsonPath("$.player.handicap", is(24)))
                .andExpect(jsonPath("$.player.rank", is(2)))
                .andExpect(jsonPath("$.player.score", is(40)))
        ;
    }

    @Test
    void listApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");
        // action
        mockMvc.perform(get("/api/v1/contests").header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].joined", is(true)))
                .andExpect(jsonPath("$[1].joined", is(true)))
                .andExpect(jsonPath("$[2].joined", is(false)))
                .andExpect(jsonPath("$[3].joined", is(false)))
                .andExpect(jsonPath("$[4].joined", is(false)))
                .andExpect(jsonPath("$[5].joined", is(false)))
        ;
    }

    @Test
    void register() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        String name = "2021 리그전";
        String description = "리그전 대회 설명";
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
                "  \"maxJoiner\": 32\n" +
                "}";

        // action
        mockMvc.perform(post("/api/v1/contest")
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
                .andExpect(jsonPath("$.start.date", notNullValue()))
                .andExpect(jsonPath("$.start.time", notNullValue()))
                .andExpect(jsonPath("$.end", notNullValue()))
                .andExpect(jsonPath("$.end.date", notNullValue()))
                .andExpect(jsonPath("$.end.time", notNullValue()))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.code", notNullValue()))
                .andExpect(jsonPath("$.state.name", notNullValue()))
                .andExpect(jsonPath("$.maxJoiner", is(32)))
        ;
    }

    @Test
    void forbidden() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");

        String name = "2021 리그전";
        String description = "리그전 대회 설명";
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
                "  \"maxJoiner\": 32\n" +
                "}";

        // action
        mockMvc.perform(post("/api/v1/contest")
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
    void modify() throws Exception {
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
                "  \"maxJoiner\": 64\n" +
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
                .andExpect(jsonPath("$.start.date", notNullValue()))
                .andExpect(jsonPath("$.start.time", notNullValue()))
                .andExpect(jsonPath("$.end", notNullValue()))
                .andExpect(jsonPath("$.end.date", notNullValue()))
                .andExpect(jsonPath("$.end.time", notNullValue()))
                .andExpect(jsonPath("$.state", notNullValue()))
                .andExpect(jsonPath("$.state.code", notNullValue()))
                .andExpect(jsonPath("$.state.name", notNullValue()))
                .andExpect(jsonPath("$.maxJoiner", is(64)))
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
                "  \"maxJoiner\": 64\n" +
                "}";

        // action
        mockMvc.perform(put("/api/v1/contest/6")
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isBadRequest()) // 400 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
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
                "  \"maxJoiner\": 64\n" +
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

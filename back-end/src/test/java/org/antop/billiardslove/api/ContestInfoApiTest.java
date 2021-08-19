package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dto.ContestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
                .andExpect(jsonPath("$.title", is("2021 리그전")))
                .andExpect(jsonPath("$.description", is("2021.01.01~")))
                .andExpect(jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(jsonPath("$.startTime", is("00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2021-12-30")))
                .andExpect(jsonPath("$.endTime", is("23:59:59")))
                .andExpect(jsonPath("$.stateCode", is("0")))
                .andExpect(jsonPath("$.stateName", is("PROCEEDING")))
                .andExpect(jsonPath("$.maxJoiner", is(32)))
                .andExpect(jsonPath("$.player", notNullValue()))
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
                .andExpect(jsonPath("$[0].player", notNullValue()))
                .andExpect(jsonPath("$[1].player", notNullValue()))
                .andExpect(jsonPath("$[2].player", nullValue()))
                .andExpect(jsonPath("$[3].player", nullValue()))
                .andExpect(jsonPath("$[4].player", nullValue()))
                .andExpect(jsonPath("$[5].player", nullValue()))
        ;
    }

    @Test
    void register() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        ContestDto request = ContestDto.builder()
                .title("2021 리그전")
                .description("리그전 대회 설명")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(32)
                .build();

        // action
        mockMvc.perform(post("/api/v1/contest")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.startDate", notNullValue()))
                .andExpect(jsonPath("$.startTime", notNullValue()))
                .andExpect(jsonPath("$.endDate", notNullValue()))
                .andExpect(jsonPath("$.endTime", notNullValue()))
                .andExpect(jsonPath("$.stateCode", notNullValue()))
                .andExpect(jsonPath("$.stateName", notNullValue()))
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

        ContestDto request = ContestDto.builder()
                .title("2021 리그전 수정")
                .description("리그전 대회 수정")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(64)
                .build();

        // action
        mockMvc.perform(put("/api/v1/contest/5")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.startDate", notNullValue()))
                .andExpect(jsonPath("$.startTime", notNullValue()))
                .andExpect(jsonPath("$.endDate", notNullValue()))
                .andExpect(jsonPath("$.endTime", notNullValue()))
                .andExpect(jsonPath("$.stateCode", notNullValue()))
                .andExpect(jsonPath("$.stateName", notNullValue()))
                .andExpect(jsonPath("$.maxJoiner", is(64)))
        ;
    }

    @Test
    void alreadyContestEnd() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("1");

        ContestDto request = ContestDto.builder()
                .title("2021 리그전 수정")
                .description("리그전 대회 수정")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(64)
                .build();

        // action
        mockMvc.perform(put("/api/v1/contest/6")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
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

        ContestDto request = ContestDto.builder()
                .title("2021 리그전 수정")
                .description("리그전 대회 수정")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(64)
                .build();

        // action
        mockMvc.perform(put("/api/v1/contest/1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
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

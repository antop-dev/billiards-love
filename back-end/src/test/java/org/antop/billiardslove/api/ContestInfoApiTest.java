package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.RestDocsUtils.Attributes;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.dto.ContestDto.Fields.DESCRIPTION;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.MAX_JOINER;
import static org.antop.billiardslove.dto.ContestDto.Fields.PLAYER;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.STATE_CODE;
import static org.antop.billiardslove.dto.ContestDto.Fields.STATE_NAME;
import static org.antop.billiardslove.dto.ContestDto.Fields.TITLE;
import static org.antop.billiardslove.dto.PlayerDto.Fields.HANDICAP;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NICKNAME;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NUMBER;
import static org.antop.billiardslove.dto.PlayerDto.Fields.RANK;
import static org.antop.billiardslove.dto.PlayerDto.Fields.SCORE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
        mockMvc.perform(get("/api/v1/contest/{id}", 1).header(HttpHeaders.AUTHORIZATION, token))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-info",
                        pathParameters(parameterWithName("id").description("대회 아이디").attributes(Attributes.type(JsonFieldType.NUMBER))),
                        requestHeaders(RestDocsUtils.jwtToken()),
                        responseFields(
                                fieldWithPath(ContestDto.Fields.ID).description("대회 아이디"),
                                fieldWithPath(TITLE).description("대회명"),
                                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath(START_DATE).description("시작 날짜"),
                                fieldWithPath(START_TIME).description("시작 시간").optional(),
                                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                                fieldWithPath(END_TIME).description("종료 시간").optional(),
                                fieldWithPath(STATE_CODE).description("상태 코드"),
                                fieldWithPath(STATE_NAME).description("상태명"),
                                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional(),
                                fieldWithPath(PLAYER).description("내 선수 정보").optional(),
                                fieldWithPath(PLAYER + "." + PlayerDto.Fields.ID).description("선수 아이디"),
                                fieldWithPath(PLAYER + "." + NICKNAME).description("선수 별명"),
                                fieldWithPath(PLAYER + "." + NUMBER).description("선수 번호 (참가 번호)").optional(),
                                fieldWithPath(PLAYER + "." + HANDICAP).description("참가 핸디캡 (회원의 핸디캡과 참가 핸디캡은 다를 수 있다.").optional(),
                                fieldWithPath(PLAYER + "." + RANK).description("순위").optional(),
                                fieldWithPath(PLAYER + "." + SCORE).description("점수").optional()
                        )
                ))
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
                .andDo(document("contest-list",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        responseFields(
                                fieldWithPath("[]." + ContestDto.Fields.ID).description("대회 아이디"),
                                fieldWithPath("[]." + TITLE).description("대회명"),
                                fieldWithPath("[]." + DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath("[]." + START_DATE).description("시작 날짜"),
                                fieldWithPath("[]." + START_TIME).description("시작 시간").optional(),
                                fieldWithPath("[]." + END_DATE).description("종료 날짜").optional(),
                                fieldWithPath("[]." + END_TIME).description("종료 시간").optional(),
                                fieldWithPath("[]." + STATE_CODE).description("상태 코드"),
                                fieldWithPath("[]." + STATE_NAME).description("상태명"),
                                fieldWithPath("[]." + MAX_JOINER).description("최대 참가자 수").optional(),
                                fieldWithPath("[]." + PLAYER).description("내 선수 정보").optional(),
                                fieldWithPath("[]." + PLAYER + "." + PlayerDto.Fields.ID).description("선수 아이디"),
                                fieldWithPath("[]." + PLAYER + "." + NICKNAME).description("선수 별명"),
                                fieldWithPath("[]." + PLAYER + "." + NUMBER).description("선수 번호 (참가 번호)").optional(),
                                fieldWithPath("[]." + PLAYER + "." + HANDICAP).description("참가 핸디캡 (회원의 핸디캡과 참가 핸디캡은 다를 수 있다.").optional(),
                                fieldWithPath("[]." + PLAYER + "." + RANK).description("순위").optional(),
                                fieldWithPath("[]." + PLAYER + "." + SCORE).description("점수").optional()
                        )
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].player").exists())
                .andExpect(jsonPath("$[1].player").exists())
                .andExpect(jsonPath("$[2].player").doesNotExist())
                .andExpect(jsonPath("$[3].player").doesNotExist())
                .andExpect(jsonPath("$[4].player").doesNotExist())
                .andExpect(jsonPath("$[5].player").doesNotExist())
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
                .andDo(document("contest-register",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("로그인 API 응답으로 받은 JWT 토큰값 (관리자 권한)")
                        ),
                        requestFields(
                                fieldWithPath(TITLE).description("대회명"),
                                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath(START_DATE).description("시작 날짜"),
                                fieldWithPath(START_TIME).description("시작 시간").optional(),
                                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                                fieldWithPath(END_TIME).description("종료 시간").optional(),
                                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional()
                        ),
                        responseFields(
                                fieldWithPath(ContestDto.Fields.ID).description("대회 아이디"),
                                fieldWithPath(TITLE).description("대회명"),
                                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath(START_DATE).description("시작 날짜"),
                                fieldWithPath(START_TIME).description("시작 시간").optional(),
                                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                                fieldWithPath(END_TIME).description("종료 시간").optional(),
                                fieldWithPath(STATE_CODE).description("상태 코드"),
                                fieldWithPath(STATE_NAME).description("상태명"),
                                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional()
                        )
                ))
                // verify
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, matchesPattern("^.*/api/v1/contest/[0-9]+$")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(jsonPath("$.startTime", is("00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2021-12-31")))
                .andExpect(jsonPath("$.endTime", is("23:59:59")))
                .andExpect(jsonPath("$.stateCode", is(ContestState.PREPARING.getCode())))
                .andExpect(jsonPath("$.stateName", is(ContestState.PREPARING.name())))
                .andExpect(jsonPath("$.maxJoiner", is(32)))
        ;
    }

    @Test
    void forbidden() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");

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
                // document
                .andDo(RestDocsUtils.error("error-forbidden"))
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
        mockMvc.perform(put("/api/v1/contest/{id}", 5)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-modify",
                        pathParameters(
                                parameterWithName("id").description("대회 아이디").attributes(Attributes.type(JsonFieldType.NUMBER))
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("로그인 API 응답으로 받은 JWT 토큰값")
                        ),
                        requestFields(
                                fieldWithPath(TITLE).description("대회명"),
                                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath(START_DATE).description("시작 날짜"),
                                fieldWithPath(START_TIME).description("시작 시간").optional(),
                                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                                fieldWithPath(END_TIME).description("종료 시간").optional(),
                                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional()
                        ),
                        responseFields(
                                fieldWithPath(ContestDto.Fields.ID).description("대회 아이디"),
                                fieldWithPath(TITLE).description("대회명"),
                                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                                fieldWithPath(START_DATE).description("시작 날짜"),
                                fieldWithPath(START_TIME).description("시작 시간").optional(),
                                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                                fieldWithPath(END_TIME).description("종료 시간").optional(),
                                fieldWithPath(STATE_CODE).description("상태 코드"),
                                fieldWithPath(STATE_NAME).description("상태명"),
                                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional()
                        )
                ))
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
        mockMvc.perform(put("/api/v1/contest/{id}", 6)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-contest-end"))
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
        mockMvc.perform(put("/api/v1/contest/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-contest-progress"))
                // verify
                .andExpect(status().isInternalServerError()) // 500 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is("이미 진행된 대회입니다.")))
        ;
    }

}

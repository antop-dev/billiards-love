package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.exception.AlreadyContestEndException;
import org.antop.billiardslove.exception.AlreadyContestProgressException;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.antop.billiardslove.util.TemporalUtils;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.dto.ContestDto.Fields.DESCRIPTION;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.MAX_JOINER;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.TITLE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ContestInfoApi.class)
class ContestInfoApiTest extends WebMvcBase {
    @MockBean
    private ContestService contestService;

    @DisplayName("대회 상세 조회")
    @Test
    void informationApi() throws Exception {
        // stub
        PlayerDto player = PlayerDto.builder()
                .id(332)
                .number(3)
                .nickname("안탑")
                .handicap(22)
                .rank(11)
                .score(1154)
                .build();
        ContestDto contest = ContestDto.builder()
                .id(5L)
                .title("대회 타이틀")
                .description("대회 설명")
                .startDate(LocalDate.of(2020, 1, 1))
                .startTime(LocalTime.MIN)
                .endDate(LocalDate.of(2020, 3, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .stateCode(ContestState.PROCEEDING.getCode())
                .stateName(stateName(ContestState.PROCEEDING))
                .maxJoiner(128)
                .player(player)
                .build();
        when(contestService.getContest(anyLong())).thenReturn(Optional.of(contest));
        // action
        mockMvc.perform(get("/api/v1/contest/{id}", 5)
                        .header(HttpHeaders.AUTHORIZATION, userToken()))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-info",
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        responseFields(RestDocsUtils.FieldSnippet.contestWithPlayer())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(contest.getId())))
                .andExpect(jsonPath("$.title", is(contest.getTitle())))
                .andExpect(jsonPath("$.description", is(contest.getDescription())))
                .andExpect(jsonPath("$.startDate", is(TemporalUtils.format(contest.getStartDate()))))
                .andExpect(jsonPath("$.startTime", is(TemporalUtils.format(contest.getStartTime()))))
                .andExpect(jsonPath("$.endDate", is(TemporalUtils.format(contest.getEndDate()))))
                .andExpect(jsonPath("$.endTime", is(TemporalUtils.format(contest.getEndTime()))))
                .andExpect(jsonPath("$.stateCode", is(contest.getStateCode())))
                .andExpect(jsonPath("$.stateName", is(contest.getStateName())))
                .andExpect(jsonPath("$.maxJoiner", is(contest.getMaxJoiner())))
                .andExpect(jsonPath("$.player").exists())
                .andExpect(jsonPath("$.player.id", NumberMatcher.is(player.getId())))
                .andExpect(jsonPath("$.player.number", is(player.getNumber())))
                .andExpect(jsonPath("$.player.nickname", is(player.getNickname())))
                .andExpect(jsonPath("$.player.handicap", is(player.getHandicap())))
                .andExpect(jsonPath("$.player.rank", is(player.getRank())))
                .andExpect(jsonPath("$.player.score", is(player.getScore())))
        ;
    }

    @DisplayName("대회 목록 조회")
    @Test
    void listApi() throws Exception {
        // stub
        List<ContestDto> contests = Arrays.asList(
                ContestDto.builder()
                        .id(1121L)
                        .title("2020년 7월 동호회 리그전")
                        .description("총 상금 100만원!")
                        .startDate(LocalDate.of(2021, 7, 1))
                        .startTime(LocalTime.of(18, 0, 0))
                        .endDate(LocalDate.of(2020, 7, 31))
                        .endTime(LocalTime.of(22, 0, 0))
                        .stateCode(ContestState.END.getCode())
                        .stateName(stateName(ContestState.END))
                        .maxJoiner(128)
                        .player(PlayerDto.builder()
                                .id(335)
                                .number(16)
                                .nickname("띠용")
                                .handicap(18)
                                .rank(5)
                                .score(600)
                                .build())
                        .build(),
                ContestDto.builder()
                        .id(1121L)
                        .title("2022년 1분기 대회")
                        .startDate(LocalDate.of(2022, 1, 1))
                        .startTime(LocalTime.of(18, 0, 0))
                        .endDate(LocalDate.of(2022, 3, 31))
                        .stateCode(ContestState.PREPARING.getCode())
                        .stateName(stateName(ContestState.PREPARING))
                        .maxJoiner(64)
                        .build()
        );
        when(contestService.getAllContests()).thenReturn(contests);

        // action
        mockMvc.perform(get("/api/v1/contests").header(HttpHeaders.AUTHORIZATION, userToken()))
                // logging
                .andDo(print())
                .andDo(document("contest-list",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        responseFields(RestDocsUtils.FieldSnippet.contestsWithPlayer())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(contests.size())))
                .andExpect(jsonPath("$[0].player").exists())
                .andExpect(jsonPath("$[1].player").doesNotExist())
        ;
    }

    @DisplayName("대회 등록")
    @Test
    void register() throws Exception {
        // stub
        when(contestService.register(any(ContestDto.class))).thenAnswer(invocation -> {
                    ContestDto request = invocation.getArgument(0, ContestDto.class);
                    return ContestDto.builder()
                            .id(987_654_321L)
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .startDate(request.getStartDate())
                            .startTime(request.getStartTime())
                            .endDate(request.getEndDate())
                            .endTime(request.getEndTime())
                            .stateCode(ContestState.PREPARING.getCode())
                            .stateName(stateName(ContestState.PREPARING))
                            .maxJoiner(request.getMaxJoiner())
                            .build();
                }
        );
        // request
        final ContestDto request = ContestDto.builder()
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
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                .andDo(document("contest-register",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        requestFields(request()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                // verify
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, matchesPattern("^.*/api/v1/contest/987654321$")))
                .andExpect(jsonPath("$.id", is(987_654_321)))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.startDate", is(TemporalUtils.format(request.getStartDate()))))
                .andExpect(jsonPath("$.startTime", is(TemporalUtils.format(request.getStartTime()))))
                .andExpect(jsonPath("$.endDate", is(TemporalUtils.format(request.getEndDate()))))
                .andExpect(jsonPath("$.endTime", is(TemporalUtils.format(request.getEndTime()))))
                .andExpect(jsonPath("$.stateCode", is(ContestState.PREPARING.getCode())))
                .andExpect(jsonPath("$.stateName", is(stateName(ContestState.PREPARING))))
                .andExpect(jsonPath("$.maxJoiner", is(request.getMaxJoiner())))
        ;
    }

    @DisplayName("권한 없음")
    @Test
    void forbidden() throws Exception {
        // request
        ContestDto request = ContestDto.builder().build();
        // action
        mockMvc.perform(post("/api/v1/contest")
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .content(toJson(request))
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

    @DisplayName("대회 수정")
    @Test
    void modify() throws Exception {
        // stub
        when(contestService.modify(anyLong(), any(ContestDto.class))).thenAnswer(invocation -> {
                    long contestId = invocation.getArgument(0, Long.class);
                    ContestDto request = invocation.getArgument(1, ContestDto.class);
                    return ContestDto.builder()
                            .id(contestId)
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .startDate(request.getStartDate())
                            .startTime(request.getStartTime())
                            .endDate(request.getEndDate())
                            .endTime(request.getEndTime())
                            .stateCode(ContestState.PREPARING.getCode())
                            .stateName(stateName(ContestState.PREPARING))
                            .maxJoiner(request.getMaxJoiner())
                            .build();
                }
        );
        // request
        ContestDto request = ContestDto.builder()
                .title("2021 리그전 수정")
                .description("리그전 대회 수정")
                .startDate(LocalDate.of(2021, 1, 2))
                .startTime(LocalTime.of(0, 10, 0))
                .endDate(LocalDate.of(2021, 6, 1))
                .endTime(LocalTime.of(10, 59, 59))
                .maxJoiner(64)
                .build();

        // action
        mockMvc.perform(put("/api/v1/contest/{id}", 5)
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-modify",
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        requestFields(request()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.startDate", is(TemporalUtils.format(request.getStartDate()))))
                .andExpect(jsonPath("$.startTime", is(TemporalUtils.format(request.getStartTime()))))
                .andExpect(jsonPath("$.endDate", is(TemporalUtils.format(request.getEndDate()))))
                .andExpect(jsonPath("$.endTime", is(TemporalUtils.format(request.getEndTime()))))
                .andExpect(jsonPath("$.stateCode", is(ContestState.PREPARING.getCode())))
                .andExpect(jsonPath("$.stateName", is(stateName(ContestState.PREPARING))))
                .andExpect(jsonPath("$.maxJoiner", is(request.getMaxJoiner())))
        ;
    }

    @DisplayName("이미 종료된 대회는 수정할 수 없다.")
    @Test
    void alreadyContestEnd() throws Exception {
        // when
        when(contestService.modify(anyLong(), any())).thenThrow(new AlreadyContestEndException());
        // request
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
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .content(toJson(request))
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

    @DisplayName("이미 진행하고 있는 대회는 수정할 수 없다.")
    @Test
    void alreadyContestProgress() throws Exception {
        // when
        when(contestService.modify(anyLong(), any())).thenThrow(new AlreadyContestProgressException());
        // request
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
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-contest-progress"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("이미 진행된 대회입니다.")))
        ;
    }

    /**
     * 대회 등록, 수정에 사용하는 필드 정의
     */
    private FieldDescriptor[] request() {
        return new FieldDescriptor[]{
                fieldWithPath(TITLE).description("대회명"),
                fieldWithPath(DESCRIPTION).description("대회 설명").optional(),
                fieldWithPath(START_DATE).description("시작 날짜"),
                fieldWithPath(START_TIME).description("시작 시간").optional(),
                fieldWithPath(END_DATE).description("종료 날짜").optional(),
                fieldWithPath(END_TIME).description("종료 시간").optional(),
                fieldWithPath(MAX_JOINER).description("최대 참가자 수").optional()
        };
    }

}

package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.ContestEndException;
import org.antop.billiardslove.exception.ContestProceedingException;
import org.antop.billiardslove.mapper.ContestMapperImpl;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.antop.billiardslove.service.PlayerService;
import org.antop.billiardslove.util.TemporalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import util.JsonUtils;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
@Import(ContestMapperImpl.class)
class ContestInfoApiTest extends WebMvcBase {
    @MockBean
    private ContestService contestService;
    /*
     아래 두개 서비스는 ContestMapper에서 사용한다.
     하지만 Model → Dto 변환에서는 사용하지 않으므로 껍대기만 만들어놓기!
     */
    @MockBean
    private PlayerService playerService;

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
                            .stateCode(ContestState.PREPARING.name())
                            .stateName(stateName(ContestState.PREPARING))
                            .maxJoiner(request.getMaxJoiner())
                            .build();
                }
        );
        // request
        final ContestInfoApi.Model request = ContestInfoApi.Model.builder()
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
                        .content(JsonUtils.toJson(request))
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
                .andExpect(jsonPath("$.stateCode", is(ContestState.PREPARING.name())))
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
                        .content(JsonUtils.toJson(request))
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
                            .stateCode(ContestState.PREPARING.name())
                            .stateName(stateName(ContestState.PREPARING))
                            .maxJoiner(request.getMaxJoiner())
                            .build();
                }
        );
        // request
        ContestInfoApi.Model request = ContestInfoApi.Model.builder()
                .title("2021 리그전 수정")
                .description("리그전 대회 수정")
                .startDate(LocalDate.of(2021, 1, 2))
                .startTime(LocalTime.of(0, 10, 0))
                .endDate(LocalDate.of(2021, 6, 1))
                .endTime(LocalTime.of(10, 59, 59))
                .maxJoiner(64)
                .build();
        // action
        mockMvc.perform(put("/api/v1/contest/{contestId}", 5)
                        .header(HttpHeaders.AUTHORIZATION, managerToken())
                        .content(JsonUtils.toJson(request))
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
                .andExpect(jsonPath("$.stateCode", is(ContestState.PREPARING.name())))
                .andExpect(jsonPath("$.stateName", is(stateName(ContestState.PREPARING))))
                .andExpect(jsonPath("$.maxJoiner", is(request.getMaxJoiner())))
        ;
    }

    @DisplayName("이미 종료된 대회는 수정할 수 없다.")
    @Test
    void alreadyContestEnd() throws Exception {
        // when
        when(contestService.modify(anyLong(), any())).thenThrow(new ContestEndException());
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
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-contest-end"))
                // verify
                .andExpect(status().isBadRequest()) // 400 에러
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("종료된 대회입니다.")))
        ;
    }

    @DisplayName("이미 진행하고 있는 대회는 수정할 수 없다.")
    @Test
    void alreadyContestProgress() throws Exception {
        // when
        when(contestService.modify(anyLong(), any())).thenThrow(new ContestProceedingException());
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
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-already-contest-progress"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("진행중인 대회입니다.")))
        ;
    }

    /**
     * 대회 등록, 수정에 사용하는 필드 정의
     */
    private FieldDescriptor[] request() {
        return new FieldDescriptor[]{
                fieldWithPath(ContestInfoApi.Model.Fields.TITLE).description("대회명"),
                fieldWithPath(ContestInfoApi.Model.Fields.DESCRIPTION).description("대회 설명").optional(),
                fieldWithPath(ContestInfoApi.Model.Fields.START_DATE).description("시작 날짜"),
                fieldWithPath(ContestInfoApi.Model.Fields.START_TIME).description("시작 시간").optional(),
                fieldWithPath(ContestInfoApi.Model.Fields.END_DATE).description("종료 날짜").optional(),
                fieldWithPath(ContestInfoApi.Model.Fields.END_TIME).description("종료 시간").optional(),
                fieldWithPath(ContestInfoApi.Model.Fields.MAX_JOINER).description("최대 참가자 수").optional()
        };
    }

}

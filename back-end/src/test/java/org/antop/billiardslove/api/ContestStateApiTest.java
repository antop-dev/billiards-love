package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.AlreadyContestEndException;
import org.antop.billiardslove.exception.CantStartContestStateException;
import org.antop.billiardslove.exception.CantStopContestStateException;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContestStateApi.class)
class ContestStateApiTest extends WebMvcBase {
    @MockBean
    private ContestService contestService;

    @DisplayName("대회를 오픈한다.")
    @Test
    void open() throws Exception {
        // when
        when(contestService.open(anyLong())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("오픈한 대회")
                        .description("오픈해서 상태가 접수중!")
                        .startDate(LocalDate.of(2020, 1, 1))
                        .startTime(LocalTime.of(19, 0, 0))
                        .endDate(LocalDate.of(2020, 1, 1))
                        .endTime(LocalTime.of(22, 0, 0))
                        .maxJoiner(16)
                        .stateCode(ContestState.ACCEPTING.getCode())
                        .stateName(stateName(ContestState.ACCEPTING))
                        .build()
        );
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/open", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-open",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.ACCEPTING.getCode())))
                .andExpect(jsonPath("$.stateName", is("접수중")))
        ;
    }

    @DisplayName("대회를 시작한다.")
    @Test
    void start() throws Exception {
        // when
        when(contestService.start(anyLong())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("시작된 대회")
                        .description("★ 시작을 했습니다!!")
                        .startDate(LocalDate.of(2020, 1, 1))
                        .startTime(LocalTime.of(19, 0, 0))
                        .maxJoiner(64)
                        .stateCode(ContestState.PROCEEDING.getCode())
                        .stateName(stateName(ContestState.PROCEEDING))
                        .build()
        );
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/start", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-start",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.PROCEEDING.getCode())))
                .andExpect(jsonPath("$.stateName", is("진행중")))
        ;
    }

    @DisplayName("대회 상태가 접수중/중지인 대회만 시작이 가능하다.")
    @Test
    void canNotStart() throws Exception {
        // when
        when(contestService.start(anyLong())).thenThrow(new CantStartContestStateException());
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/start", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-start-can-not"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("대회를 시작할 수 없는 상태입니다.")))
        ;
    }

    @DisplayName("대회를 중지한다.")
    @Test
    void stop() throws Exception {
        // when
        when(contestService.stop(anyLong())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("중지한 대회")
                        .startDate(LocalDate.of(2021, 8, 7))
                        .stateCode(ContestState.STOPPED.getCode())
                        .stateName(stateName(ContestState.STOPPED))
                        .build()
        );
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/stop", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                .andDo(print())
                // document
                .andDo(document("contest-stop",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.STOPPED.getCode())))
                .andExpect(jsonPath("$.stateName", is("중지")))
        ;
    }

    @DisplayName("진행중인 대회만 중지할 수 있다.")
    @Test
    void canNotStop() throws Exception {
        // when
        when(contestService.stop(anyLong())).thenThrow(new CantStopContestStateException());
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/stop", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-stop-can-not"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("대회를 중지할 수 없는 상태입니다.")))
        ;
    }

    @DisplayName("대회를 종료한다.")
    @Test
    void end() throws Exception {
        // when
        when(contestService.end(anyLong())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("종료한 대회")
                        .startDate(LocalDate.of(2021, 1, 1))
                        .stateCode(ContestState.END.getCode())
                        .stateName(stateName(ContestState.END))
                        .build()
        );
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/end", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-end",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.END.getCode())))
                .andExpect(jsonPath("$.stateName", is("종료")))
        ;
    }

    @DisplayName("이미 종료된 대회는 다시 종료할 수 없다.")
    @Test
    void alreadyEnd() throws Exception {
        // when
        when(contestService.end(anyLong())).thenThrow(new AlreadyContestEndException());
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/end", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, managerToken()))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-end-already"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("이미 종료된 대회입니다.")))
        ;
    }

}

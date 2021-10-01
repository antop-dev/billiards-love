package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.api.ContestJoiningApi.JoiningRequest;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.exception.AlreadyJoinException;
import org.antop.billiardslove.exception.CanNotJoinContestStateException;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.api.ContestJoiningApi.JoiningRequest.Fields.HANDICAP;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContestJoiningApi.class)
class ContestJoiningApiTest extends WebMvcBase {
    @MockBean
    private ContestService contestService;

    @DisplayName("대회에 참가한다.")
    @Test
    void join() throws Exception {
        // when
        int handicap = 30;
        final PlayerDto player = PlayerDto.builder()
                .id(faker.number().randomNumber())
                .handicap(handicap)
                .nickname("선수1")
                .build();
        when(contestService.join(anyLong(), anyLong(), anyInt())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("참가 가능한 대회")
                        .startDate(LocalDate.of(2020, 7, 1))
                        .stateCode(ContestState.ACCEPTING.getCode())
                        .stateName(stateName(ContestState.ACCEPTING))
                        .player(player)
                        .build()
        );
        // request
        JoiningRequest request = new JoiningRequest(handicap);
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 2)
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, userToken()))
                // logging
                .andDo(print())
                // document
                .andDo(document("contest-join",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        requestFields(
                                fieldWithPath(HANDICAP).description("참가 핸디캡").optional()
                        ),
                        responseFields(RestDocsUtils.FieldSnippet.contestWithPlayer())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateCode", is(ContestState.ACCEPTING.getCode())))
                .andExpect(jsonPath("$.stateName", is(stateName(ContestState.ACCEPTING))))
                .andExpect(jsonPath("$.player").exists())
                .andExpect(jsonPath("$.player.id").exists())
                .andExpect(jsonPath("$.player.handicap", is(handicap)))
        ;
    }

    @DisplayName("참가할 수 없는 상태의 대회에 참가할 수 없다?")
    @Test
    void canNotJoining() throws Exception {
        // when
        when(contestService.join(anyLong(), anyLong(), anyInt())).thenThrow(new CanNotJoinContestStateException());
        // request
        JoiningRequest request = new JoiningRequest(30);
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 1)
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, userToken()))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-join-can-not"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("대회에 참가할 수 없는 상태입니다.")))

        ;
    }

    @DisplayName("이미 참가한 대회는 다시 참가할 수 없다.")
    @Test
    void alreadyJoining() throws Exception {
        // when
        when(contestService.join(anyLong(), anyLong(), anyInt())).thenThrow(new AlreadyJoinException());
        // request
        JoiningRequest request = new JoiningRequest(30);
        // action
        mockMvc.perform(post("/api/v1/contest/{id}/join", 2)
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, userToken()))
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("contest-join-already"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("이미 참가한 대회입니다.")))

        ;
    }

    @DisplayName("참가를 취소한다.")
    @Test
    void cancelJoining() throws Exception {
        // when
        when(contestService.cancelJoin(anyLong(), anyLong())).then(invocation ->
                ContestDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .title("최소한 대회")
                        .description("player 속성이 없다.")
                        .startDate(LocalDate.of(2021, 1, 10))
                        .endDate(LocalDate.of(2021, 11, 12))
                        .maxJoiner(128)
                        .stateCode(ContestState.ACCEPTING.getCode())
                        .stateName(stateName(ContestState.ACCEPTING))
                        .build()
        );
        // request
        long contestId = 2;
        // action
        mockMvc.perform(delete("/api/v1/contest/{id}/join", contestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, userToken()))
                .andDo(print())
                .andDo(document("contest-join-cancel",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        pathParameters(RestDocsUtils.PathParameter.contestId()),
                        responseFields(RestDocsUtils.FieldSnippet.contest())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(contestId)))
                .andExpect(jsonPath("$.player").doesNotExist())
        ;
    }

}

package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContestJoiningApi.class)
class ContestJoiningApiTest extends WebMvcBase {
    @MockBean
    private ContestService contestService;

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
                        .stateCode(ContestState.ACCEPTING.name())
                        .stateName(stateName(ContestState.ACCEPTING))
                        .build()
        );
        // request
        long contestId = 2;
        // action
        mockMvc.perform(delete("/api/v1/contest/{contestId}/join", contestId)
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

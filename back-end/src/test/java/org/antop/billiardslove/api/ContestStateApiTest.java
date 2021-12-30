package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.ContestEndException;
import org.antop.billiardslove.model.ContestState;
import org.antop.billiardslove.service.ContestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

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
        mockMvc.perform(post("/api/v1/contest/{contestId}/end", 2)
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
        when(contestService.end(anyLong())).thenThrow(new ContestEndException());
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
                .andExpect(jsonPath("$.message", is("종료된 대회입니다.")))
        ;
    }

}

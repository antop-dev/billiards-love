package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils.Attributes;
import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.dto.MatchDto.Fields.OPPONENT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class MatchApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void matches() throws Exception {
        // 대회 아이디
        long contestId = 1;
        // 회원 아이디
        long memberId = 2;
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("" + memberId);

        mockMvc.perform(get("/api/v1/contest/{id}/matches", contestId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // document
                .andDo(document("matches",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(
                                parameterWithName("id").description("대회 아이디").attributes(Attributes.type(JsonFieldType.NUMBER))
                        ),
                        responseFields(
                                fieldWithPath("[]." + MatchDto.Fields.ID).description("경기 아이디"),
                                fieldWithPath("[]." + MatchDto.Fields.RESULT).description("경기 결과"),
                                fieldWithPath("[]." + MatchDto.Fields.CLOSED).description("확정 여부 +\ntrue : 수정 불가"),
                                fieldWithPath("[]." + OPPONENT).description("선수 정보"),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.ID).description("선수 아이디"),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.NICKNAME).description("별명"),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.HANDICAP).description("선수 아이디"),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.NUMBER).description("선수 번호").optional(),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.RANK).description("순위").optional(),
                                fieldWithPath("[]." + OPPONENT + "." + PlayerDto.Fields.SCORE).description("점수").optional()
                        )
                ))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].opponent.id", is(1)))
                .andExpect(jsonPath("$[0].opponent.number", is(1)))
                .andExpect(jsonPath("$[0].opponent.nickname", is("안탑")))
                .andExpect(jsonPath("$[0].result[0]", is("LOSE")))
                .andExpect(jsonPath("$[0].result[1]", is("LOSE")))
                .andExpect(jsonPath("$[0].result[2]", is("WIN")))
                .andExpect(jsonPath("$[0].closed", is(true)))
                .andExpect(jsonPath("$[1].closed", is(false)))
        ;
    }

    @Test
    void match() throws Exception {
        // 경기 아이디
        long matchId = 5;
        // 회원 아이디
        long memberId = 3;
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("" + memberId);

        mockMvc.perform(get("/api/v1/match/{id}", matchId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                // logging
                .andDo(print())
                // document
                .andDo(document("match",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(
                                parameterWithName("id").description("경기 아이디").attributes(Attributes.type(JsonFieldType.NUMBER))
                        ),
                        responseFields(
                                fieldWithPath(MatchDto.Fields.ID).description("경기 아이디"),
                                fieldWithPath(MatchDto.Fields.RESULT).description("경기 결과"),
                                fieldWithPath(MatchDto.Fields.CLOSED).description("확정 여부 +\ntrue : 수정 불가"),
                                fieldWithPath(OPPONENT).description("선수 정보"),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.ID).description("선수 아이디"),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.NICKNAME).description("별명"),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.HANDICAP).description("선수 아이디"),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.NUMBER).description("선수 번호").optional(),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.RANK).description("순위").optional(),
                                fieldWithPath(OPPONENT + "." + PlayerDto.Fields.SCORE).description("점수").optional()
                        )
                ))
                // verify
                .andExpect(jsonPath("$.id", NumberMatcher.is(matchId)))
                .andExpect(jsonPath("$.opponent.id", is(2)))
                .andExpect(jsonPath("$.opponent.number", is(2)))
                .andExpect(jsonPath("$.opponent.nickname", is("띠용")))
                .andExpect(jsonPath("$.result[0]", is("WIN")))
                .andExpect(jsonPath("$.result[1]", is("WIN")))
                .andExpect(jsonPath("$.result[2]", is("WIN")))
                .andExpect(jsonPath("$.closed", is(false)))
        ;
    }

}

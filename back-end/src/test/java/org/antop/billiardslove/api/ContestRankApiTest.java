package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils.Attributes;
import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.dto.PlayerDto.Fields.HANDICAP;
import static org.antop.billiardslove.dto.PlayerDto.Fields.ID;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NICKNAME;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NUMBER;
import static org.antop.billiardslove.dto.PlayerDto.Fields.RANK;
import static org.antop.billiardslove.dto.PlayerDto.Fields.SCORE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContestRankApiTest extends SpringBootBase {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void rankApi() throws Exception {
        // request
        String token = jwtTokenProvider.createToken("2");
        // action
        mockMvc.perform(
                        get("/api/v1/contest/{id}/ranks", 1)
                                .header(HttpHeaders.AUTHORIZATION, token)
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("ranks",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        pathParameters(
                                parameterWithName("id").description("대회 아이디").attributes(Attributes.type(JsonFieldType.NUMBER))
                        ),
                        responseFields(
                                fieldWithPath("[]." + ID).description("선수 아이디"),
                                fieldWithPath("[]." + NICKNAME).description("별명"),
                                fieldWithPath("[]." + HANDICAP).description("선수 아이디"),
                                fieldWithPath("[]." + NUMBER).description("선수 번호").optional(),
                                fieldWithPath("[]." + RANK).description("순위").optional(),
                                fieldWithPath("[]." + SCORE).description("점수").optional()
                        )
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
        ;

    }
}

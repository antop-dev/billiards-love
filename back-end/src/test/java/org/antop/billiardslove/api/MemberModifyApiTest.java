package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.api.MemberModifyApi.Request;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.util.Aes256Util;
import org.hamcrest.Aes256Matcher;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.encrypted;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberModifyApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void modify() throws Exception {
        // 회원 아이디
        long memberId = 1;
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("" + memberId);
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 변경할 정보
        String nickname = faker.name().fullName();
        int handicap = 30;
        // 요청 JSON
        Request request = new Request(Aes256Util.encrypt(nickname, secretKey), handicap);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                // document
                .andDo(document("member-modify",
                        requestHeaders(RestDocsUtils.jwtToken()),
                        requestFields(
                                fieldWithPath(Request.Fields.NICKNAME).description("별명").attributes(encrypted()),
                                fieldWithPath(Request.Fields.HANDICAP).description("핸디캡")
                        ),
                        responseFields(RestDocsUtils.FieldsSnippet.member())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(memberId)))
                .andExpect(jsonPath("$.nickname", Aes256Matcher.is(nickname, secretKey)))
                .andExpect(jsonPath("$.handicap", is(handicap)))
        ;
    }

    @Test
    void mustNicknameNotEmpty() throws Exception {
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("1");
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 별명이 null
        Request request = new Request("", 30);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andDo(RestDocsUtils.error("member-modify-valid-nickname-not-empty"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("별명을 입력해주세요.")))
        ;
    }

    @Test
    void mustHandicapNotNull() throws Exception {
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("1");
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 핸디캡이 null
        Request request = new Request("NICKNAME", null);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andDo(RestDocsUtils.error("member-modify-valid-handicap-not-null"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("핸디캡을 입력해주세요.")))
        ;
    }

    @Test
    void mustHandicapIsOverOne() throws Exception {
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("1");
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 핸디캡을 0으로 입력
        Request request = new Request("NICKNAME", 0);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andDo(RestDocsUtils.error("member-modify-valid-handicap-over-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("핸디캡을 1 이상 입력해주세요.")))
        ;
    }

    @Test
    void notFoundMember() throws Exception {
        // 생성한 JWT 토큰
        String token = jwtTokenProvider.createToken("9999");
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 요청
        Request request = new Request(Aes256Util.encrypt("Antop", secretKey), 30);
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-bad-credentials"))
                // verify
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(401)))
                .andExpect(jsonPath("$.message", is("회원을 찾을 수 없습니다.")))
        ;
    }

}

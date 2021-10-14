package org.antop.billiardslove.api;

import org.antop.billiardslove.RestDocsUtils;
import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.api.MemberModifyApi.Request;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.service.MemberService;
import org.antop.billiardslove.util.Aes256Util;
import org.hamcrest.Aes256Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.CustomAttributes.encrypted;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberModifyApi.class)
class MemberModifyApiTest extends WebMvcBase {
    @MockBean
    private MemberService memberService;

    @Test
    void modify() throws Exception {
        // when
        when(memberService.modify(anyLong(), anyString(), anyInt())).then(invocation ->
                MemberDto.builder()
                        .id(invocation.getArgument(0, Long.class))
                        .nickname(invocation.getArgument(1, String.class))
                        .thumbnail("https://img.foo.bar")
                        .handicap(invocation.getArgument(2, Integer.class))
                        .build()
        );
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 변경할 정보
        String nickname = "변경된 별명";
        int handicap = 30;
        // request
        Request request = new Request(Aes256Util.encrypt(nickname, secretKey), handicap);
        // action
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("member-modify",
                        requestHeaders(RestDocsUtils.Header.jwtToken()),
                        requestFields(
                                fieldWithPath(Request.Fields.NICKNAME).description("별명").attributes(encrypted()),
                                fieldWithPath(Request.Fields.HANDICAP).description("핸디캡")
                        ),
                        responseFields(RestDocsUtils.FieldSnippet.member())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", Aes256Matcher.is(nickname, secretKey)))
                .andExpect(jsonPath("$.handicap", is(handicap)))
        ;
    }

    @Test
    void mustNicknameNotEmpty() throws Exception {
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 별명이 null
        Request request = new Request("", 30);
        // action
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("member-modify-valid-nickname-not-empty"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("별명을 입력해주세요.")))
        ;
    }

    @Test
    void mustHandicapNotNull() throws Exception {
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 핸디캡이 null
        Request request = new Request("NICKNAME", null);
        // action
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
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
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 핸디캡을 0으로 입력
        Request request = new Request("NICKNAME", 0);
        // action
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("member-modify-valid-handicap-over-1"))
                // verify
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("핸디캡을 1 이상 입력해주세요.")))
        ;
    }

    @Test
    void notFoundMember() throws Exception {
        when(memberService.modify(anyLong(), anyString(), anyInt())).thenThrow(new MemberNotFoundException());
        // AES256 암호화 키
        String secretKey = Aes256Util.generateKey();
        // 요청
        Request request = new Request(Aes256Util.encrypt("Antop", secretKey), 30);
        mockMvc.perform(put("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, userToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
                )
                // logging
                .andDo(print())
                // document
                .andDo(RestDocsUtils.error("error-bad-credentials"))
                // verify
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("회원을 찾을 수 없습니다.")))
        ;
    }

}

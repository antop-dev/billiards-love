package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.api.LoggedInApi.Request;
import org.antop.billiardslove.api.LoggedInApi.Response.Member;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.encrypted;
import static org.antop.billiardslove.api.LoggedInApi.Request.Fields.CONNECTED_AT;
import static org.antop.billiardslove.api.LoggedInApi.Request.Fields.PROFILE;
import static org.antop.billiardslove.api.LoggedInApi.Request.Profile.Fields.IMAGE_URL;
import static org.antop.billiardslove.api.LoggedInApi.Request.Profile.Fields.NEEDS_AGREEMENT;
import static org.antop.billiardslove.api.LoggedInApi.Request.Profile.Fields.NICKNAME;
import static org.antop.billiardslove.api.LoggedInApi.Request.Profile.Fields.THUMBNAIL_URL;
import static org.antop.billiardslove.api.LoggedInApi.Response.Fields.MEMBER;
import static org.antop.billiardslove.api.LoggedInApi.Response.Fields.REGISTERED;
import static org.antop.billiardslove.api.LoggedInApi.Response.Fields.TOKEN;
import static org.hamcrest.IsJwtToken.isJwtToken;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoggedInApiTest extends SpringBootBase {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 처음 로그인한다.
     */
    @Test
    void firstLogin() throws Exception {
        // request
        LoggedInApi.Request request = Request.builder()
                .id(9999)
                .connectedAt(ZonedDateTime.of(2020, 8, 17, 15, 45, 4, 0, ZoneId.of("UTC")))
                .profile(Request.Profile.builder()
                        .nickname("antop")
                        .imageUrl("https://cataas.com/cat?width=200&height=200")
                        .thumbnailUrl("https://cataas.com/cat?width=160&height=100")
                        .needsAgreement(true)
                        .build())
                .build();

        // action
        mockMvc.perform(post("/api/v1/logged-in")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", isJwtToken(jwtProperties.getSecretKey())))
                .andExpect(jsonPath("$.registered", is(false)))
                .andExpect(jsonPath("$.member", notNullValue()))
                .andExpect(jsonPath("$.member.id", greaterThan(0)))
                .andExpect(jsonPath("$.member.nickname", is(request.getProfile().getNickname())))
                .andExpect(jsonPath("$.member.thumbnail", is(request.getProfile().getThumbnailUrl())))
                .andExpect(jsonPath("$.member.handicap").doesNotExist())
        ;
    }

    /**
     * 추가 회원정보까지 입력이 된 상태
     */
    @Test
    void reLogin() throws Exception {
        LoggedInApi.Request request = Request.builder()
                .id(1)
                .connectedAt(ZonedDateTime.of(2020, 8, 17, 15, 45, 4, 0, ZoneId.of("Europe/Paris")))
                .profile(Request.Profile.builder()
                        .nickname("Antop")
                        .imageUrl("https://foo")
                        .thumbnailUrl("https://bar")
                        .needsAgreement(true)
                        .build())
                .build();
        // action
        mockMvc.perform(post("/api/v1/logged-in")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("logged-in",
                        requestFields(
                                fieldWithPath(Request.Fields.ID).description("카카오톡 회원번호"),
                                fieldWithPath(CONNECTED_AT).description(
                                        "카카오톡 +++<a href=\"https://developers.kakao.com/docs/latest/ko/kakaologin/common#link-and-unlink\">연결</a>++ 완료된 시각, UTC +\n"
                                                + " (+++<a href=\"https://tools.ietf.org/html/rfc3339\">RFC3339 internet date/time format</a>+++)"),
                                fieldWithPath(PROFILE + "." + NICKNAME).description("닉네임").attributes(encrypted()),
                                fieldWithPath(PROFILE + "." + IMAGE_URL).description("프로필 이미지 URL +\n640px * 640px 또는 480px * 480px"),
                                fieldWithPath(PROFILE + "." + THUMBNAIL_URL).description("프로필 미리보기 이미지 URL +\n110px * 110px 또는 100px * 100px"),
                                fieldWithPath(PROFILE + "." + NEEDS_AGREEMENT).description("사용자 동의 시 프로필 제공 가능")
                        ),
                        responseFields(
                                fieldWithPath(TOKEN).description("JWT 토큰"),
                                fieldWithPath(REGISTERED).description("추가 정보 등록됨 여부 +\nfalse면 추가 정보를 입력 받아야 한다."),
                                fieldWithPath(MEMBER + "." + Member.Fields.ID).description("회원 아이디"),
                                fieldWithPath(MEMBER + "." + Member.Fields.THUMBNAIL).description("회원 이미지 (카카오톡 프로필 썸네일)"),
                                fieldWithPath(MEMBER + "." + Member.Fields.NICKNAME)
                                        .description("회원 별명 or 카카오톡 별명")
                                        .attributes(encrypted()),
                                fieldWithPath(MEMBER + "." + Member.Fields.HANDICAP)
                                        .description("3구 핸디캡")
                                        .optional()
                        )

                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", isJwtToken(jwtProperties.getSecretKey())))
                .andExpect(jsonPath("$.registered", is(true)))
                .andExpect(jsonPath("$.member", notNullValue()))
                .andExpect(jsonPath("$.member.id", is(1)))
                // 회원의 별명은 유지된다.
                .andExpect(jsonPath("$.member.nickname", is("안탑")))
                // width=100 → width=80
                .andExpect(jsonPath("$.member.thumbnail", is(request.getProfile().getThumbnailUrl())))
                .andExpect(jsonPath("$.member.handicap", is(22)))
        ;
    }

}

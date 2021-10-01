package org.antop.billiardslove.api;

import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.api.LoggedInApi.Request;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.service.LoggedInService;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.CustomAttributes.encrypted;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoggedInApi.class)
class LoggedInApiTest extends WebMvcBase {
    @MockBean
    private LoggedInService loggedInService;
    @SpyBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 한다.")
    @Test
    void firstLogin() throws Exception {
        // when
        final long memberId = 12; // 생성될 회원 아이디
        when(jwtTokenProvider.createToken(any())).thenReturn("created jwt token value!");
        when(loggedInService.loggedIn(any(KakaoDto.class))).then(invocation -> {
            KakaoDto kakao = invocation.getArgument(0, KakaoDto.class);
            return MemberDto.builder()
                    .id(memberId)
                    .nickname(kakao.getNickname())
                    .thumbnail(kakao.getThumbnailUrl())
                    .build();
        });
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
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // logging
                .andDo(print())
                // document
                .andDo(document("logged-in",
                        requestFields(loginRequestFields()),
                        responseFields(loginResponseFields())
                ))
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.registered", is(false)))
                .andExpect(jsonPath("$.member").exists())
                .andExpect(jsonPath("$.member.id", NumberMatcher.is(memberId)))
                .andExpect(jsonPath("$.member.nickname", is(request.getProfile().getNickname())))
                .andExpect(jsonPath("$.member.thumbnail", is(request.getProfile().getThumbnailUrl())))
                .andExpect(jsonPath("$.member.handicap").doesNotExist())
        ;
    }

    @DisplayName("회원이 이미 한번 로그인(가입)했던 상태에서 로그인")
    @Test
    void reLogin() throws Exception {
        // when
        final String oldNickname = "안탑";
        final long memberId = 12;
        when(jwtTokenProvider.createToken(any())).thenReturn("created jwt token value!");
        when(loggedInService.loggedIn(any(KakaoDto.class))).then(invocation -> {
            KakaoDto kakao = invocation.getArgument(0, KakaoDto.class);
            return MemberDto.builder()
                    .id(memberId)
                    .nickname(oldNickname)
                    .thumbnail(kakao.getThumbnailUrl())
                    .handicap(22)
                    .build();
        });
        // request
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
                        .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("logged-in-registered",
                        requestFields(loginRequestFields()),
                        responseFields(loginResponseFields())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.registered", is(true)))
                .andExpect(jsonPath("$.member").exists())
                .andExpect(jsonPath("$.member.id", NumberMatcher.is(memberId)))
                // 회원의 별명은 유지된다.
                .andExpect(jsonPath("$.member.nickname", is(oldNickname)))
                // 썸네일은 변경됨
                .andExpect(jsonPath("$.member.thumbnail", is(request.getProfile().getThumbnailUrl())))
                .andExpect(jsonPath("$.member.handicap", is(22)))
        ;
    }

    private List<FieldDescriptor> loginRequestFields() {
        return Arrays.asList(
                fieldWithPath(LoggedInApi.Request.Fields.ID).description("카카오톡 회원번호"),
                fieldWithPath(LoggedInApi.Request.Fields.CONNECTED_AT).description(
                        "카카오톡 +++<a href=\"https://developers.kakao.com/docs/latest/ko/kakaologin/common#link-and-unlink\">연결</a>++ 완료된 시각, UTC +\n"
                                + " (+++<a href=\"https://tools.ietf.org/html/rfc3339\">RFC3339 internet date/time format</a>+++)"),
                fieldWithPath(LoggedInApi.Request.Fields.PROFILE + "." + LoggedInApi.Request.Profile.Fields.NICKNAME).description("닉네임").attributes(encrypted()),
                fieldWithPath(LoggedInApi.Request.Fields.PROFILE + "." + LoggedInApi.Request.Profile.Fields.IMAGE_URL).description("프로필 이미지 URL +\n640px * 640px 또는 480px * 480px"),
                fieldWithPath(LoggedInApi.Request.Fields.PROFILE + "." + LoggedInApi.Request.Profile.Fields.THUMBNAIL_URL).description("프로필 미리보기 이미지 URL +\n110px * 110px 또는 100px * 100px"),
                fieldWithPath(LoggedInApi.Request.Fields.PROFILE + "." + LoggedInApi.Request.Profile.Fields.NEEDS_AGREEMENT).description("사용자 동의 시 프로필 제공 가능")
        );
    }

    private List<FieldDescriptor> loginResponseFields() {
        return Arrays.asList(
                fieldWithPath(LoggedInApi.Response.Fields.TOKEN).description("JWT 토큰"),
                fieldWithPath(LoggedInApi.Response.Fields.REGISTERED).description("추가 정보 등록됨 여부 +\nfalse면 추가 정보를 입력 받아야 한다."),
                fieldWithPath(LoggedInApi.Response.Fields.MEMBER + "." + LoggedInApi.Response.Member.Fields.ID).description("회원 아이디"),
                fieldWithPath(LoggedInApi.Response.Fields.MEMBER + "." + LoggedInApi.Response.Member.Fields.THUMBNAIL).description("회원 이미지 (카카오톡 프로필 썸네일)"),
                fieldWithPath(LoggedInApi.Response.Fields.MEMBER + "." + LoggedInApi.Response.Member.Fields.NICKNAME).description("회원 별명").attributes(encrypted()),
                fieldWithPath(LoggedInApi.Response.Fields.MEMBER + "." + LoggedInApi.Response.Member.Fields.HANDICAP).description("핸디캡").type(NUMBER).optional()
        );
    }

}

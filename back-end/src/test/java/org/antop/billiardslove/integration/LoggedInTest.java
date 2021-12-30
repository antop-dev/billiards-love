package org.antop.billiardslove.integration;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.TestSecurityConfig;
import org.antop.billiardslove.api.LoggedInApi;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.util.Aes256Util;
import org.hamcrest.Aes256Matcher;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import util.JsonUtils;

import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@DisplayName("카카오톡 로그인 테스트")
class LoggedInTest extends SpringBootBase {

    @DisplayName("관리자 로그인")
    @Test
    void loggedInManager() throws Exception {
        // given
        String secretKey = Aes256Util.generateKey();
        long kakaoId = 1L; // 이 카카오 로그인을 쓰는 회원은 관리자이다.
        String thumbnailUrl = "https://placedog.net/100";

        LoggedInApi.Request request = LoggedInApi.Request.builder()
                .id(kakaoId)
                .connectedAt(ZonedDateTime.now())
                .profile(LoggedInApi.Request.Profile.builder()
                        .imageUrl("https://placedog.net/500")
                        .thumbnailUrl(thumbnailUrl)
                        .nickname(Aes256Util.encrypt("Antop", secretKey))
                        .needsAgreement(false)
                        .build())
                .build();
        // when
        ResultActions actions = mockMvc.perform(
                        post("/api/v1/logged-in")
                                .sessionAttr(Security.SECRET_KEY, secretKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.toJson(request))
                )
                .andDo(print());
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.member.id", NumberMatcher.is(1L)))
                .andExpect(jsonPath("$.member.nickname", Aes256Matcher.is("안탑", secretKey)))
                .andExpect(jsonPath("$.member.handicap", is(22)))
                .andExpect(jsonPath("$.member.thumbnail", is(thumbnailUrl)))
                .andExpect(jsonPath("$.member.manager", is(true)))
                .andExpect(jsonPath("$.registered", is(true)))
        ;
    }

    @DisplayName("신규 카카오톡 로그인 통합 테스트")
    @Test
    void loggedInNewUser() throws Exception {
        // given
        String secretKey = Aes256Util.generateKey();
        long kakaoId = 9999L; // 신규 회원
        String nickname = "신규유저";
        String thumbnailUrl = "https://placedog.net/100";

        LoggedInApi.Request request = LoggedInApi.Request.builder()
                .id(kakaoId)
                .connectedAt(ZonedDateTime.now())
                .profile(LoggedInApi.Request.Profile.builder()
                        .imageUrl("https://placedog.net/500")
                        .thumbnailUrl(thumbnailUrl)
                        .nickname(Aes256Util.encrypt(nickname, secretKey))
                        .needsAgreement(false)
                        .build())
                .build();
        // when
        ResultActions actions = mockMvc.perform(
                        post("/api/v1/logged-in")
                                .sessionAttr(Security.SECRET_KEY, secretKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.toJson(request))
                )
                .andDo(print());
        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.member.id", NumberMatcher.is(7L))) // 신규 회원 = 7
                .andExpect(jsonPath("$.member.nickname", Aes256Matcher.is(nickname, secretKey)))
                .andExpect(jsonPath("$.member.handicap").doesNotExist()) // 신규 로그인은 핸디캡이 없다.
                .andExpect(jsonPath("$.member.thumbnail", is(thumbnailUrl)))
                .andExpect(jsonPath("$.member.manager", is(false)))
                .andExpect(jsonPath("$.registered", is(false)))
        ;
    }

}

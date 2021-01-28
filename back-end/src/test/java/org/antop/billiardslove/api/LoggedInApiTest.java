package org.antop.billiardslove.api;

import org.antop.billiardslove.MockMvcBase;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.IsJwtToken.isJwtToken;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoggedInApiTest extends MockMvcBase {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private KakaoRepository kakaoRepository;
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 처음 로그인(+회원 가입) 했거나 추가 회원정보를 아직 입력하지 않은 상태
     */
    @Test
    void firstLogin() throws Exception {
        // request
        String nickname = "antop";
        String thumbnailUrl = "https://cataas.com/cat?width=160&height=100";
        String requestBody = "{\n" +
                "  \"id\": 9999,\n" +
                "  \"connectedAt\": \"2020-08-17T15:45:04Z\",\n" +
                "  \"profile\": {\n" +
                "    \"nickname\": \"" + nickname + "\",\n" +
                "    \"imageUrl\": \"https://cataas.com/cat?width=200&height=200\",\n" +
                "    \"thumbnailUrl\": \"" + thumbnailUrl + "\",\n" +
                "    \"needsAgreement\": true\n" +
                "  }\n" +
                "}";
        // action
        mockMvc.perform(post("/api/v1/logged-in")
                .content(requestBody)
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
                .andExpect(jsonPath("$.member.nickname", is(nickname)))
                .andExpect(jsonPath("$.member.thumbnail", is(thumbnailUrl)))
                .andExpect(jsonPath("$.member.handicap", nullValue()))
        ;
    }

    /**
     * 추가 회원정보까지 입력이 된 상태
     */
    @Test
    void reLogin() throws Exception {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(111L)
                .connectedAt(LocalDateTime.now().minusDays(30))
                .profile(KakaoProfile.builder()
                        .nickname("Jack")
                        .thumbUrl("https://cataas.com/cat?width=160&height=100")
                        .imgUrl("https://cataas.com/cat?width=160&height=200")
                        .build())
                .build();
        kakaoRepository.saveAndFlush(kakaoLogin);

        Member member = Member.builder()
                .kakaoLogin(kakaoLogin)
                .nickname("Antop") // 이미 회원 닉네임은 정해짐
                .handicap(30)
                .build();
        memberRepository.saveAndFlush(member);

        // request
        String nickname = "Mr. Hong";
        String thumbnailUrl = "https://cataas.com/cat?width=80&height=80";
        String requestBody = "{\n" +
                "  \"id\": 111,\n" +
                "  \"connectedAt\": \"2020-11-17T15:45:04Z\",\n" +
                "  \"profile\": {\n" +
                "    \"nickname\": \"" + nickname + "\",\n" +
                "    \"imageUrl\": \"https://cataas.com/cat?width=200&height=200\",\n" +
                "    \"thumbnailUrl\": \"" + thumbnailUrl + "\",\n" +
                "    \"needsAgreement\": true\n" +
                "  }\n" +
                "}";
        // action
        mockMvc.perform(post("/api/v1/logged-in")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        )
                // logging
                .andDo(print())
                // verify
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", isJwtToken(jwtProperties.getSecretKey())))
                .andExpect(jsonPath("$.registered", is(true)))
                .andExpect(jsonPath("$.member", notNullValue()))
                .andExpect(jsonPath("$.member.id", is(member.getId().intValue())))
                .andExpect(jsonPath("$.member.nickname", is(member.getNickname())))
                // width=100 → width=80
                .andExpect(jsonPath("$.member.thumbnail", is(thumbnailUrl)))
                .andExpect(jsonPath("$.member.handicap", is(member.getHandicap())))
        ;
    }

}

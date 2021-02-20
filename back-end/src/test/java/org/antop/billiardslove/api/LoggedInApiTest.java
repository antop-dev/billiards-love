package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.IsJwtToken.isJwtToken;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoggedInApiTest extends SpringBootBase {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private KakaoRepository kakaoRepository;
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 처음 로그인한다.
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
        // request
        String thumbnailUrl = "https://bar";
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"connectedAt\": \"2020-11-17T15:45:04Z\",\n" +
                "  \"profile\": {\n" +
                "    \"nickname\": \"Antop™\",\n" +
                "    \"imageUrl\": \"https://foo\",\n" +
                "    \"thumbnailUrl\": \"" + thumbnailUrl + "\",\n" +
                "    \"needsAgreement\": true\n" +
                "  }\n" +
                "}";
        // action
        mockMvc.perform(post("/api/v1/logged-in")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", isJwtToken(jwtProperties.getSecretKey())))
                .andExpect(jsonPath("$.registered", is(true)))
                .andExpect(jsonPath("$.member", notNullValue()))
                .andExpect(jsonPath("$.member.id", is(1)))
                // 회원의 별명은 유지된다.
                .andExpect(jsonPath("$.member.nickname", is("안탑")))
                // width=100 → width=80
                .andExpect(jsonPath("$.member.thumbnail", is(thumbnailUrl)))
                .andExpect(jsonPath("$.member.handicap", is(22)))
        ;
    }

}

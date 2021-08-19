package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.util.Aes256Util;
import org.hamcrest.Aes256Matcher;
import org.hamcrest.NumberMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String json = "{\n" +
                "  \"nickname\": \"" + Aes256Util.encrypt(nickname, secretKey) + "\",\n" +
                "  \"handicap\": " + handicap + "\n" +
                "}";

        mockMvc.perform(
                post("/api/v1/member")
                        .sessionAttr(Security.SECRET_KEY, secretKey)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", NumberMatcher.is(memberId)))
                .andExpect(jsonPath("$.nickname", Aes256Matcher.is(nickname, secretKey)))
                .andExpect(jsonPath("$.handicap", is(handicap)))
        ;
    }

}

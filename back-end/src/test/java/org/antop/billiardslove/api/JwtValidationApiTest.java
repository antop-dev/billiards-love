package org.antop.billiardslove.api;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwtValidationApiTest extends SpringBootBase {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void test() throws Exception {
        // 회원 아이디로 JWT 토큰 생성
        String token = jwtTokenProvider.createToken("2");

        // API 호출
        mockMvc.perform(get("/jwt").header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("2")))
                // https://docs.spring.io/spring-security/site/docs/5.3.6.RELEASE/reference/html5/#authenticated-assertion
                .andExpect(authenticated().withAuthentication(auth -> {
                    assertThat(auth.getName(), is("2"));
                    assertThat(auth.getPrincipal(), is(2L));
                    assertThat(auth.getCredentials(), is(token));
                    assertThat(auth.getDetails(), nullValue());
                }));
    }

}

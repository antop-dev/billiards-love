package org.antop.billiardslove.api;

import org.antop.billiardslove.MockMvcBase;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwtValidationApiTest extends MockMvcBase {
    @Autowired
    private KakaoRepository kakaoRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void test() throws Exception {
        // 테스트에 사용할 회원 생성
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(999999L)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder()
                        .nickname(faker.name().fullName())
                        .imgUrl("https://cataas.com/cat?width=200&height=200")
                        .thumbUrl("https://cataas.com/cat?width=100&height=100")
                        .build())
                .build();
        kakaoRepository.save(kakaoLogin);

        final Member member = Member.builder()
                .kakaoLogin(kakaoLogin)
                .nickname(faker.name().fullName())
                .handicap(21)
                .build();
        memberRepository.save(member);

        // 회원 아이디로 JWT 토큰 생성
        String token = jwtTokenProvider.createToken(member.getId());

        // API 호출
        mockMvc.perform(get("/jwt").header(HttpHeaders.AUTHORIZATION, token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("" + member.getId())))
                // https://docs.spring.io/spring-security/site/docs/5.3.6.RELEASE/reference/html5/#authenticated-assertion
                .andExpect(authenticated().withAuthentication(auth -> {
                    assertThat(auth.getName(), is("" + member.getId()));
                    assertThat(auth.getPrincipal(), is(member.getId()));
                    assertThat(auth.getCredentials(), is(token));
                    assertThat(auth.getDetails(), nullValue());
                }));
    }

}

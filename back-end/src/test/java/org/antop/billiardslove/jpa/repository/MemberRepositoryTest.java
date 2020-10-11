package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnableJpaAuditing
@DisplayName("회원 테스트")
class MemberRepositoryTest extends DataJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KakaoRepository kakaoRepository;

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    void insert() {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(999182L)
                .profile(KakaoProfile.builder().nickname("김철수").imgUrl("").thumbUrl("").build())
                .connectedAt(LocalDateTime.now())
                .build();
        kakaoRepository.save(kakaoLogin);

        Member member = new Member();
        member.setLoginToken("LoginToken&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member.setKakaoLogin(kakaoLogin);
        memberRepository.save(member);

        assertThat(member.getId(), notNullValue());
        assertThat(member.getRegisterDateTime(), notNullValue());
        assertThat(member.getKakaoLogin(), notNullValue());
        assertThat(member.getLoginToken(), notNullValue());
    }

    @Test
    void insert_read() {

    }

    @Test
    void insert_delete() {

    }

}

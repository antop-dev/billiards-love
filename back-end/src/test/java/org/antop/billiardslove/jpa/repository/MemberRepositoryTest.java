package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
        KakaoLogin kakaoLogin = new KakaoLogin();
        kakaoLogin.setAccessToken("accessToken&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
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
        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        Member member1 = new Member();
        member1.setLoginToken("LoginToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member1.setKakaoLogin(kakaoLogin1);
        memberRepository.save(member1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        Member member2 = new Member();
        member2.setLoginToken("LoginToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member2.setKakaoLogin(kakaoLogin2);
        memberRepository.save(member2);

        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList, hasSize(2));
        assertThat(memberList, contains(member1, member2));

    }

    @Test
    void insert_delete() {
        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        Member member1 = new Member();
        member1.setLoginToken("LoginToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member1.setKakaoLogin(kakaoLogin1);
        memberRepository.save(member1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        Member member2 = new Member();
        member2.setLoginToken("LoginToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member2.setKakaoLogin(kakaoLogin2);
        memberRepository.save(member2);

        memberRepository.deleteAll();
        assertThat(memberRepository.findAll(), IsEmptyCollection.empty());
    }

}

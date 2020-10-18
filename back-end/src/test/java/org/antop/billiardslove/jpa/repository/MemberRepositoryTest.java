package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@EnableJpaAuditing
@DisplayName("회원 테스트")
class MemberRepositoryTest extends DataJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KakaoRepository kakaoRepository;

    @Test
    @DisplayName("회원 데이터를 조회한다.")
    void AoEKf() {
        Optional<Member> optional = memberRepository.findById(2L);
        assertThat(optional.isPresent(), is(true));
        Member member = optional.get();
        assertThat(member.getNickname(), is("띠용"));
    }

    @Test
    @DisplayName("회원 데이터를 등록한다.")
    void OeDOf() {
        Optional<KakaoLogin> optional = kakaoRepository.findById(1213141503L);

        Member member = Member.builder()
                .nickname("골드스푼")
                .handicap(30)
                .kakaoLogin(optional.get())
                .loginToken("loginToken1")
                .build();
        memberRepository.save(member);
        System.out.println(member);

        Optional<Member> optional1 = memberRepository.findById(7L);
        assertThat(optional1.isPresent(), is(true));
        Member member1 = optional1.get();
        assertThat(member1.getNickname(), is("골드스푼"));
    }

    @Test
    @DisplayName("회원 프로필을 갱신한다.")
    void J6l1Z() {
        memberRepository.findById(3L).ifPresent(it -> {
            it.setNickname("골드스푼");
            it.setHandicap(30);
            it.setLoginToken("loginTokenChange1");
        });

        Optional<Member> optional = memberRepository.findById(3L);
        assertThat(optional.isPresent(), is(true));
        Member member = optional.get();
        assertThat(member.getNickname(), is("골드스푼"));
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        Optional<KakaoLogin> optional = kakaoRepository.findById(1213141503L);

        Member member = Member.builder()
                .id(3L)
                .registerDateTime(LocalDateTime.now())
                .nickname("골드스푼")
                .handicap(30)
                .loginToken("loginToken1")
                .kakaoLogin(optional.get())
                .build();
        memberRepository.save(member);

        Optional<Member> optional1 = memberRepository.findById(3L);
        assertThat(optional1.isPresent(), is(true));
        Member member1 = optional1.get();
        assertThat(member1.getNickname(), is("골드스푼"));
    }
}

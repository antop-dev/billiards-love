package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("회원 테스트")
class MemberRepositoryTest extends DataJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KakaoRepository kakaoRepository;

    @Test
    @DisplayName("회원 데이터를 조회한다.")
    void AoEKf() {
        Optional<Member> memberOptional = memberRepository.findById(2L);
        assertThat(memberOptional.isPresent(), is(true));
        Member member = memberOptional.get();
        assertThat(member.getNickname(), is("띠용"));
        assertThat(member.getHandicap(), is(20));
        assertThat(member.getRegisterDateTime(), notNullValue());
        assertThat(member.getLoginToken(), is("803fa52145da1c0cc9a748018a95d131"));
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
        List<Member> list = memberRepository.findAll();
        assertThat(list, hasSize(6));
    }

    @Test
    @DisplayName("회원 데이터를 등록한다.")
    void OeDOf() {
        Optional<KakaoLogin> kakaoLoginOptional = kakaoRepository.findById(1213141503L);

        Member member = Member.builder()
                .nickname("골드스푼")
                .handicap(30)
                .kakaoLogin(kakaoLoginOptional.get())
                .loginToken("loginToken1")
                .build();
        memberRepository.save(member);

        Optional<Member> memberOptional = memberRepository.findById(7L);
        assertThat(memberOptional.isPresent(), is(true));
        Member member1 = memberOptional.get();
        assertThat(member1.getNickname(), is("골드스푼"));
        assertThat(member.getHandicap(), is(30));
        assertThat(member.getRegisterDateTime(), notNullValue());
        assertThat(member.getLoginToken(), is("loginToken1"));
    }

    @Test
    @DisplayName("회원 프로필을 갱신한다.")
    void J6l1Z() {
        memberRepository.findById(3L).ifPresent(it -> {
            it.setNickname("골드스푼");
            it.setHandicap(30);
            it.setLoginToken("loginTokenChange1");
        });

        Optional<Member> memberOptional = memberRepository.findById(3L);
        assertThat(memberOptional.isPresent(), is(true));
        Member member = memberOptional.get();
        assertThat(member.getNickname(), is("골드스푼"));
        assertThat(member.getHandicap(), is(30));
        assertThat(member.getRegisterDateTime(), notNullValue());
        assertThat(member.getLoginToken(), is("loginTokenChange1"));
    }

}

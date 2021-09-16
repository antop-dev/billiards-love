package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class MemberRepositoryTest extends SpringBootBase {
    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("회원이 관리자인치 체크한다.")
    void findManager() {
        // 2. 실행
        Optional<Member> optional = repository.findById(1L);

        // 3. 검증
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(member -> {
            assertThat(member.getKakao(), notNullValue());
            assertThat(member.getNickname(), is("안탑"));
            assertThat(member.getHandicap(), is(22));
            assertThat(member.getCreated(), notNullValue());
            assertThat(member.getModified(), nullValue());
            assertThat(member.isManager(), is(true));
        });
    }

    @Test
    @DisplayName("회원정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao)
                .build();

        repository.save(member);
        flushAndClear();

        // 2. 실행
        Optional<Member> optional = repository.findById(member.getId());
        assertThat(optional.isPresent(), is(true));

        // 3. 검증
        optional.ifPresent(memberData -> {
            assertThat(memberData.getKakao(), notNullValue());
            assertThat(memberData.getNickname(), is("골드스푼"));
            assertThat(memberData.getHandicap(), nullValue());
            assertThat(memberData.getCreated(), notNullValue());
            assertThat(memberData.getModified(), notNullValue());
            assertThat(memberData.isManager(), is(false));
        });
    }

    @Test
    @DisplayName("카카오 정보가 존재하지 않는다.")
    void notKakao() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();
        // 2. 실행
        Optional<Member> optional = repository.findByKakao(kakao);

        // 3. 검증
        assertThat(optional.isPresent(), is(false));
    }

    @Test
    @DisplayName("멤버정보를 삭제한다.")
    void delete() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao)
                .build();

        repository.save(member);
        flushAndClear();

        // 2. 실행
        repository.deleteById(member.getId());
        flushAndClear();

        // 3. 검증
        Assertions.assertThrows(MemberNotFoundException.class, () -> {
            repository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        });
    }
}

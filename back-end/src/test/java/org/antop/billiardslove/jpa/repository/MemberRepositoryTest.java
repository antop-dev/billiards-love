package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.DataJpaBase;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class MemberRepositoryTest extends DataJpaBase {
    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("회원정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        final Member member = member();
        member.setManager(true);

        // 2. 실행
        repository.save(member);
        assertThat(member.getId(), notNullValue());
        assertThat(member.getCreated(), notNullValue());

        flushAndClear();

        // 3. 검증
        Optional<Member> optional = repository.findById(member.getId());
        assertThat(optional, isPresent());
        optional.ifPresent(it -> {
            assertThat(it.getNickname(), is(member.getNickname()));
            assertThat(it.getHandicap(), nullValue());
            assertThat(it.getCreated(), notNullValue());
            assertThat(it.getModified(), notNullValue());
            assertThat(it.isManager(), is(true));
        });
    }

    @Test
    @DisplayName("카카오 정보가 존재하지 않는다.")
    void notFoundByKakao() {
        // 1. 데이터 준비
        Kakao kakao = kakao();
        // 2. 실행
        Optional<Member> optional = repository.findByKakao(kakao);
        // 3. 검증
        assertThat(optional, isEmpty());
    }

    @Test
    @DisplayName("멤버정보를 삭제한다.")
    void delete() {
        // 1. 데이터 준비
        Member member = member();
        repository.save(member);

        flushAndClear();

        // 2. 실행
        repository.deleteById(member.getId());
        flushAndClear();

        // 3. 검증
        Optional<Member> optional = repository.findById(member.getId());
        assertThat(optional, isEmpty());
    }
}

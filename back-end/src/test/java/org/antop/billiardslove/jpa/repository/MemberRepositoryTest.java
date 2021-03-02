package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class MemberRepositoryTest extends SpringBootBase {
    @Autowired
    private MemberRepository repository;

    @Test
    void findManager() {
        Optional<Member> optional = repository.findById(1L);
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
    void findNotManager() {
        Optional<Member> optional = repository.findById(2L);
        assertThat(optional.isPresent(), is(true));

        optional.ifPresent(member -> {
            assertThat(member.getKakao(), notNullValue());
            assertThat(member.getNickname(), is("띠용"));
            assertThat(member.getHandicap(), is(20));
            assertThat(member.getCreated(), notNullValue());
            assertThat(member.getModified(), notNullValue());
            assertThat(member.isManager(), is(false));
        });
    }

    @Test
    void findAll() {
        List<Member> members = repository.findAll();
        assertThat(members, hasSize(4));
    }

    @Test
    void save() {
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

        assertThat(member.getId(), notNullValue());
        assertThat(member.getId(), greaterThan(0L));
    }

}

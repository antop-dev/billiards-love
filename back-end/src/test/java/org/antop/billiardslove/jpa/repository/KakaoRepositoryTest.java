package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Kakao.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class KakaoRepositoryTest extends SpringBootBase {
    @Autowired
    private KakaoRepository repository;

    @Test
    void findById() {
        Optional<Kakao> optional = repository.findById(3L);
        assertThat(optional.isPresent(), is(true));

        optional.ifPresent(kakao -> {
            assertThat(kakao.getProfile().getNickname(), is("김정민"));
            assertThat(kakao.getConnectedAt(), notNullValue());
            assertThat(kakao.getProfile().getImgUrl(), is("https://picsum.photos/640"));
            assertThat(kakao.getProfile().getThumbUrl(), is("https://picsum.photos/110"));
        });

    }

    @Test
    void findAll() {
        List<Kakao> kakaos = repository.findAll();
        assertThat(kakaos, hasSize(3));
    }

    @Test
    void save() {
        Kakao kakao = Kakao.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(Profile.builder()
                        .nickname(faker.name().name())
                        .imgUrl("https://picsum.photos/640")
                        .thumbUrl("https://picsum.photos/110").build())
                .build();
        repository.save(kakao);
        // TODO: 뭘 검증해야 할까?
    }

    @Test
    void changeProfile() {
        repository.findById(2L).ifPresent(it -> {
            Profile newProfile = Profile.builder()
                    .nickname(faker.name().name())
                    .imgUrl("https://foo")
                    .thumbUrl("https://bar")
                    .build();
            it.changeProfile(newProfile);
        });
        // TODO: 뭘 검증 해야 할까?
    }

}

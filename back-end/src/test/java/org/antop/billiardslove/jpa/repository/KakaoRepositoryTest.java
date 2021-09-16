package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Kakao.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class KakaoRepositoryTest extends SpringBootBase {
    @Autowired
    private KakaoRepository repository;

    @Test
    @DisplayName("카카오 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        String name = faker.name().name();
        Kakao kakaoData = Kakao.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(Profile.builder()
                        .nickname(name)
                        .imgUrl("https://picsum.photos/640")
                        .thumbUrl("https://picsum.photos/110").build())
                .build();

        // 2. 실행
        repository.save(kakaoData);
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakaoData.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(kakao -> {
            assertThat(kakao.getProfile().getNickname(), is(name));
            assertThat(kakao.getConnectedAt(), notNullValue());
            assertThat(kakao.getProfile().getImgUrl(), is("https://picsum.photos/640"));
            assertThat(kakao.getProfile().getThumbUrl(), is("https://picsum.photos/110"));
        });
    }

    @Test
    @DisplayName("카카오 정보를 변경한다.")
    void changeProfile() {
        // 1. 데이터 준비
        String name = faker.name().name();
        Kakao kakaoData = Kakao.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(Profile.builder()
                        .nickname(name)
                        .imgUrl("https://picsum.photos/640")
                        .thumbUrl("https://picsum.photos/110").build())
                .build();
        repository.save(kakaoData);
        flushAndClear();

        // 2. 실행
        repository.findById(kakaoData.getId()).ifPresent(it -> {
            Profile newProfile = Profile.builder()
                    .nickname(name)
                    .imgUrl("https://foo")
                    .thumbUrl("https://bar")
                    .build();
            it.changeProfile(newProfile);
        });
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakaoData.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(kakao -> {
            assertThat(kakao.getProfile().getNickname(), is(name));
            assertThat(kakao.getConnectedAt(), notNullValue());
            assertThat(kakao.getProfile().getImgUrl(), is("https://foo"));
            assertThat(kakao.getProfile().getThumbUrl(), is("https://bar"));
        });
    }

    @Test
    @DisplayName("존재하지 않는 카카오 정보를 조회한다.")
    void delete() {
        // 1. 데이터 준비
        String name = faker.name().name();
        Kakao kakaoData = Kakao.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(Profile.builder()
                        .nickname(name)
                        .imgUrl("https://picsum.photos/640")
                        .thumbUrl("https://picsum.photos/110").build())
                .build();
        repository.save(kakaoData);
        flushAndClear();

        // 2. 실행
        repository.deleteById(kakaoData.getId());
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakaoData.getId());
        assertThat(optional.isPresent(), is(false));
    }
}

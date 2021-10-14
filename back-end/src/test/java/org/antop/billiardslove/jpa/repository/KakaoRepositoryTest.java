package org.antop.billiardslove.jpa.repository;

import com.github.npathai.hamcrestopt.OptionalMatchers;
import org.antop.billiardslove.DataJpaBase;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Kakao.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class KakaoRepositoryTest extends DataJpaBase {
    @Autowired
    private KakaoRepository repository;

    @Test
    @DisplayName("카카오 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Kakao kakao = kakao();

        // 2. 실행
        repository.save(kakao);
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakao.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(it -> {
            assertThat(it.getProfile().getNickname(), is(kakao.getProfile().getNickname()));
            assertThat(it.getConnectedAt(), notNullValue());
            assertThat(it.getProfile().getImgUrl(), is(kakao.getProfile().getImgUrl()));
            assertThat(it.getProfile().getThumbUrl(), is(kakao.getProfile().getThumbUrl()));
        });
    }

    @Test
    @DisplayName("카카오 정보를 변경한다.")
    void changeProfile() {
        // 1. 데이터 준비
        final long kakaoId = 9_999_999_999L;

        Kakao kakao = kakao(kakaoId);
        repository.save(kakao);
        flushAndClear();

        // 2. 실행
        final String newName = faker.name().name();
        final String newImageUrl = "https://foo";
        final String newThumbUrl = "https://bar";

        repository.findById(kakaoId).ifPresent(it -> {
            Profile newProfile = Profile.builder()
                    .nickname(newName)
                    .imgUrl(newImageUrl)
                    .thumbUrl(newThumbUrl)
                    .build();
            it.changeProfile(newProfile);
        });
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakaoId);
        assertThat(optional, isPresent());
        optional.ifPresent(it -> {
            assertThat(it.getProfile().getNickname(), is(newName));
            assertThat(it.getProfile().getImgUrl(), is(newImageUrl));
            assertThat(it.getProfile().getThumbUrl(), is(newThumbUrl));
        });
    }

    @Test
    @DisplayName("존재하지 않는 카카오 정보를 조회한다.")
    void delete() {
        long kakaoId = 9_999_999_998L;
        // 1. 데이터 준비
        Kakao kakao = kakao(kakaoId);
        repository.save(kakao);
        flushAndClear();

        // 2. 실행
        repository.deleteById(kakaoId);
        flushAndClear();

        // 3. 검증
        Optional<Kakao> optional = repository.findById(kakaoId);
        assertThat(optional, OptionalMatchers.isEmpty());
    }
}

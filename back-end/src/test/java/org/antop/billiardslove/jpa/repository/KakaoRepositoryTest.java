package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@EnableJpaAuditing
@DisplayName("카카오 로그인 저장소 테스트")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class KakaoRepositoryTest extends DataJpaTest {
    @Autowired
    private KakaoRepository repository;

    @Test
    @DisplayName("카카오 로그인 데이터를 조회한다.")
    void SEfEt() {
        Optional<KakaoLogin> optional = repository.findById(1213141501L);
        assertThat(optional.isPresent(), is(true));
        KakaoLogin kakaoLogin = optional.get();
        assertThat(kakaoLogin.getProfile().getNickname(), is("안정용"));
    }

    @Test
    @DisplayName("새로운 카카오 로그인 데이터를 등록한다.")
    void E6RA6() {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder().nickname("알파고").imgUrl("").thumbUrl("").build())
                .build();
        repository.save(kakaoLogin);
        System.out.println(kakaoLogin);
    }

    @Test
    @DisplayName("카카오 프로파일을 갱신한다.")
    void J6l1Z() {
        repository.findById(1213141501L).ifPresent(it -> {
            KakaoProfile newProfile = KakaoProfile.builder()
                    .nickname("Antop")
                    .imgUrl("https://foo")
                    .thumbUrl("https://bar")
                    .build();
            it.connect(LocalDateTime.now());
            it.changeProfile(newProfile);
        });
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(1213141502L)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder().nickname("침착맨").imgUrl("").thumbUrl("").build())
                .build();
        repository.save(kakaoLogin);
        System.out.println(kakaoLogin);
    }
}

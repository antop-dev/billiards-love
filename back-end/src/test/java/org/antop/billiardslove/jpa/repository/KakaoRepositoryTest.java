package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("카카오 로그인 저장소 테스트")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class KakaoRepositoryTest extends DataJpaTest {
    @Autowired
    private KakaoRepository repository;

    @Test
    @DisplayName("카카오 로그인 데이터를 조회한다.")
    void SEfEt() {
        Optional<KakaoLogin> kakaoLoginOptional = repository.findById(1213141501L);
        assertThat(kakaoLoginOptional.isPresent(), is(true));
        KakaoLogin kakaoLogin = kakaoLoginOptional.get();
        assertThat(kakaoLogin.getProfile().getNickname(), is("안정용"));
        assertThat(kakaoLogin.getConnectedAt(), notNullValue());
        assertThat(kakaoLogin.getProfile().getImgUrl(), is("https://picsum.photos/640"));
        assertThat(kakaoLogin.getProfile().getThumbUrl(), is("https://picsum.photos/110"));
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
        List<KakaoLogin> list = repository.findAll();
        assertThat(list, hasSize(6));
    }

    @Test
    @DisplayName("새로운 카카오 로그인 데이터를 등록한다.")
    void E6RA6() {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(9999999999L)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder().nickname("알파고").imgUrl("https://picsum.test/640").thumbUrl("https://picsum.test/110").build())
                .build();
        repository.save(kakaoLogin);

        Optional<KakaoLogin> kakaoLoginOptional = repository.findById(9999999999L);
        assertThat(kakaoLoginOptional.isPresent(), is(true));
        KakaoLogin kakaoLogin1 = kakaoLoginOptional.get();
        assertThat(kakaoLogin1.getProfile().getNickname(), is("알파고"));
        assertThat(kakaoLogin.getConnectedAt(), notNullValue());
        assertThat(kakaoLogin.getProfile().getImgUrl(), is("https://picsum.test/640"));
        assertThat(kakaoLogin.getProfile().getThumbUrl(), is("https://picsum.test/110"));
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

        Optional<KakaoLogin> kakaoLoginOptional = repository.findById(1213141501L);
        assertThat(kakaoLoginOptional.isPresent(), is(true));
        KakaoLogin kakaoLogin = kakaoLoginOptional.get();
        assertThat(kakaoLogin.getProfile().getNickname(), is("Antop"));
        assertThat(kakaoLogin.getConnectedAt(), notNullValue());
        assertThat(kakaoLogin.getProfile().getImgUrl(), is("https://foo"));
        assertThat(kakaoLogin.getProfile().getThumbUrl(), is("https://bar"));
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        KakaoLogin kakaoLogin = KakaoLogin.builder()
                .id(1213141502L)
                .connectedAt(LocalDateTime.now())
                .profile(KakaoProfile.builder().nickname("침착맨").imgUrl("a").thumbUrl("b").build())
                .build();
        repository.save(kakaoLogin);

        Optional<KakaoLogin> kakaoLoginOptional = repository.findById(1213141502L);
        assertThat(kakaoLoginOptional.isPresent(), is(true));
        KakaoLogin kakaoLogin1 = kakaoLoginOptional.get();
        assertThat(kakaoLogin1.getProfile().getNickname(), is("침착맨"));
        assertThat(kakaoLogin1.getConnectedAt(), notNullValue());
        assertThat(kakaoLogin1.getProfile().getImgUrl(), is("a"));
        assertThat(kakaoLogin1.getProfile().getThumbUrl(), is("b"));
    }
}

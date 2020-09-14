package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnableJpaAuditing
@DisplayName("카카오 테스트")
class KakaoRepositoryTest extends DataJpaTest {

    @Autowired
    private KakaoRepository kakaoRepository;

    @AfterEach
    void afterEach() {
        kakaoRepository.deleteAll();
    }

    @Test
    void insert() {
        KakaoLogin kakaoLogin = new KakaoLogin();

        kakaoLogin.setAccessToken("accessToken&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin);

        System.out.println(kakaoLogin.toString());

        assertThat(kakaoLogin.getId(), notNullValue());
        assertThat(kakaoLogin.getAccessToken(), notNullValue());
        assertThat(kakaoLogin.getLastConnectDateTime(), notNullValue());
    }

    @Test
    void insert_read() {
        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        KakaoLogin kakaoLogin3 = new KakaoLogin();
        kakaoLogin3.setAccessToken("accessToken3&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin3);

        List<KakaoLogin> kakaoLoginList = kakaoRepository.findAll();

        assertThat(kakaoLoginList, hasSize(3));
        assertThat(kakaoLoginList, contains(kakaoLogin1, kakaoLogin2, kakaoLogin3));

    }

    @Test
    void insert_delete() {
        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        kakaoRepository.deleteAll();

        assertThat(kakaoRepository.findAll(), IsEmptyCollection.empty());

    }

}
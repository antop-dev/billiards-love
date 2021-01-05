package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.properties.GoogleProperties;
import org.antop.billiardslove.config.properties.KakaoProperties;
import org.antop.billiardslove.util.Aes256;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 초기화 API
 *
 * @author antop
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class InitApi {
    private final KakaoProperties kakaoProperties;
    private final GoogleProperties googleProperties;

    @PostMapping("api/v1/init")
    public InitResponse init() {
        String secretKey = Aes256.generateKey();

        return InitResponse.builder()
                .secretKey(secretKey)
                .kakaoKey(Aes256.encrypt(kakaoProperties.getJavaScriptKey(), secretKey))
                .adSenseKey(Aes256.encrypt(googleProperties.getAdSenseKey(), secretKey))
                .build();
    }

    /**
     * 초기화 API 응답 클래스
     *
     * @author antop
     */
    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InitResponse {
        /**
         * AES256 암호화 키
         */
        private final String secretKey;
        /**
         * 카카오 Javascript API 키
         */
        private final String kakaoKey;
        /**
         * 구글 애드센스 키
         */
        private final String adSenseKey;
    }

}

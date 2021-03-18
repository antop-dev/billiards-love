package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.Constants;
import org.antop.billiardslove.config.properties.GoogleProperties;
import org.antop.billiardslove.config.properties.KakaoProperties;
import org.antop.billiardslove.util.Aes256Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public Response init(HttpSession session) {
        String secretKey = Aes256Util.generateKey();
        session.setAttribute(Constants.SECRET_KEY, secretKey);

        return Response.builder()
                .secretKey(secretKey)
                .kakaoKey(Aes256Util.encrypt(kakaoProperties.getJavaScriptKey(), secretKey))
                .adSenseKey(Aes256Util.encrypt(googleProperties.getAdSenseKey(), secretKey))
                .build();
    }

    /**
     * 초기화 API 응답 클래스
     *
     * @author antop
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        /**
         * AES256 암호화 키
         */
        private String secretKey;
        /**
         * 카카오 Javascript API 키
         */
        private String kakaoKey;
        /**
         * 구글 애드센스 키
         */
        private String adSenseKey;
    }

}

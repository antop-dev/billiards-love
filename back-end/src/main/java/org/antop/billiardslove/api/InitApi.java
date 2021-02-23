package org.antop.billiardslove.api;

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
    public InitResponse init(HttpSession session) {
        String secretKey = Aes256Util.generateKey();
        session.setAttribute(Constants.SECRET_KEY, secretKey);

        return InitResponse.builder()
                .secretKey(secretKey)
                .kakaoKey(Aes256Util.encrypt(kakaoProperties.getJavaScriptKey(), secretKey))
                .adSenseKey(Aes256Util.encrypt(googleProperties.getAdSenseKey(), secretKey))
                .build();
    }

}

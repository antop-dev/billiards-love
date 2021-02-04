package org.antop.billiardslove.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * 카카오 API 설정
 */
@Getter
@RequiredArgsConstructor
@ToString
@ConfigurationProperties(prefix = "kakao")
@ConstructorBinding
public class KakaoProperties {
    /**
     * JavaScript 키
     */
    private final String javaScriptKey;
    /**
     * REST API 키
     */
    private final String restKey;
}

package org.antop.billiardslove.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * 구글 관련 설정
 *
 * @author antop
 */
@Getter
@RequiredArgsConstructor
@ToString
@ConfigurationProperties(prefix = "google")
@ConstructorBinding
public class GoogleProperties {
    /**
     * AdSense
     */
    private final String adSenseKey;
    /**
     * Analytics Tracking Id
     */
    private final String analyticsKey;
}

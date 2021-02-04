package org.antop.billiardslove.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

/**
 * JWT 설정
 *
 * @author antop
 */
@Getter
@RequiredArgsConstructor
@ToString
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {
    /**
     * 토큰 암호화 키
     */
    private final String secretKey;
    /**
     * 토큰 지속 시간
     */
    private final Duration duration = Duration.ofMinutes(30);
}

package org.antop.billiardslove.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

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
    private final String secretKey;
}

package org.antop.billiardslove.jpa.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "init")
public class SecretProperties {
    private String secretKey;
}

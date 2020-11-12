package org.antop.billiardslove.jpa.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InitResponse {
    private String appId;
    private String encodeKey;
    private String kakaoKey;
}

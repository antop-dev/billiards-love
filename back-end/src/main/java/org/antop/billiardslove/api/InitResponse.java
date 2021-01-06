package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 초기화 API 응답 클래스
 *
 * @author antop
 */
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InitResponse {
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

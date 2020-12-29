package org.antop.billiardslove.jpa.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InitResponse {
    /**
     * 서버에서 만들어준 앱 아이디
     */
    private String appId;
    /**
     * 클라이언트 서버간 암호화에 사용할 키
     */
    private String encodeKey;
    /**
     * 카카오톡 자바스크립트 API 키
     */
    private String kakaoKey;
}

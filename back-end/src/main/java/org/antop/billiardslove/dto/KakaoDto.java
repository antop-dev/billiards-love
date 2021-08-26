package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldNameConstants
public class KakaoDto {
    /**
     * 카카오 회원번호
     */
    private final Long id;
    /**
     * 서비스 연결 시간
     */
    private final LocalDateTime connectedAt;
    /**
     * 닉네임
     */
    private final String nickname;
    /**
     * 프로필 이미지 Url
     */
    private final String imageUrl;
    /**
     * 프로필 미리보기 Url
     */
    private final String thumbnailUrl;
}

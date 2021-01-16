package org.antop.billiardslove.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
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
    @Setter
    private String nickname;
    /**
     * 프로필 이미지 Url
     */
    @Setter
    private String imageUrl;
    /**
     * 프로필 미리보기 Url
     */
    @Setter
    private String thumbnailUrl;
}

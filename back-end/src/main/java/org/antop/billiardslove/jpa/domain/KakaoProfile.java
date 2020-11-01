package org.antop.billiardslove.jpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

/**
 * 카카오 프로파일
 *
 * @author antop
 */
@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class KakaoProfile {
    /**
     * 닉네임
     */
    private String nickname;
    /**
     * 프로필 이미지 URL, 640px * 640px 또는 480px * 480px
     */
    private String imgUrl;
    /**
     * 프로필 미리보기 이미지 URL, 110px * 110px 또는 100px * 100px
     */
    private String thumbUrl;
}

package org.antop.billiardslove.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@ToString
public class LoggedInRequest {
    /**
     * 카카오톡 아이디
     */
    private long id;
    /**
     * 서비스에 연결 완료된 시각 (UTC)
     */
    private ZonedDateTime connectedAt;
    /**
     * 프로필
     */
    private Profile profile;

    /**
     * {@link ZonedDateTime}을 시스템 타임존으로 변경된 {@link LocalDateTime}으로 변환해서 준다.
     *
     * @return {@link LocalDateTime}
     */
    public LocalDateTime getConnectedAt() {
        return connectedAt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Profile {
        /**
         * 닉네임
         */
        private String nickname;
        /**
         * 프로필 이미지 URL
         */
        private String imageUrl;
        /**
         * 프로필 미리보기 이미지 URL
         */
        private String thumbnailUrl;
        /**
         * 사용자 동의시 프로필 제공 가능 여부
         */
        private boolean needsAgreement;
    }
}

package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoggedInResponse {
    /**
     * JWT 토큰
     */
    private final String token;
    /**
     * 추가정보 등록 여부
     */
    private boolean registered;
    /**
     * 회원
     */
    private final Member member;

    @Builder
    public LoggedInResponse(String token, Member member) {
        this.token = token;
        this.member = member;
        if (member.getHandicap() != null) {
            registered = true;
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    static class Member {
        /**
         * 회원 아이디
         */
        private final Long id;
        /**
         * 회원 별명
         */
        private final String nickname;
        /**
         * 회원 핸디캡
         */
        private final Integer handicap;
        /**
         * 회원 이미지 (카카오톡 프로필 썸네일)
         */
        private final String thumbnail;
    }
}

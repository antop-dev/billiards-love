package org.antop.billiardslove.api;

import lombok.Builder;
import lombok.Getter;

@Getter
class LoggedInResponse {
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
    private final MemberDto member;

    @Builder
    public LoggedInResponse(String token, MemberDto member) {
        this.token = token;
        this.member = member;
        if (member.getHandicap() != null) {
            registered = true;
        }
    }

}

package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
    /**
     * 회원 아이디
     */
    private final long id;
    /**
     * 회원 별명
     */
    private final String nickname;
    /**
     * 회원 핸디캡
     */
    private final int handicap;
    /**
     * 회원 이미지 (카카오톡 프로필 썸네일)
     */
    private final String thumbnail;
}

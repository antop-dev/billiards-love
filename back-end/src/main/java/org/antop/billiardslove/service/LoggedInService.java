package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;

public interface LoggedInService {
    /**
     * 카카오톡 로그인 처리
     *
     * @param kakaoDto 카카오 로그인 정보
     * @return 회원 정보
     */
    MemberDto loggedIn(KakaoDto kakaoDto);
}

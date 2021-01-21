package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;

public interface LoggedInService {
    /**
     * 카카오 정보 조회
     *
     * @param kakaoDto 로그인된 카카오 정보
     * @return 회원 정보
     */
    MemberDto registerMember(KakaoDto kakaoDto);
}

package org.antop.billiardslove.service;

import org.antop.billiardslove.api.KakaoDto;
import org.antop.billiardslove.api.MemberDto;
import org.antop.billiardslove.jpa.entity.KakaoLogin;

public interface LoggedInService {
    KakaoLogin getKakaoInfo(KakaoDto kakaoDto);

    MemberDto getMemberInfo(KakaoLogin kakaoLogin, KakaoDto kakaoDto);
}

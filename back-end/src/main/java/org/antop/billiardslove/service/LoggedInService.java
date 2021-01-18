package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;

public interface LoggedInService {
    MemberDto registerMember(KakaoDto kakaoDto);
}

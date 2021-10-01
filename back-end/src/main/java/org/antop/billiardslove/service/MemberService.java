package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.MemberDto;

import java.util.Optional;

public interface MemberService {

    /**
     * 회원 정보를 수정한다.
     *
     * @param memberId 회원 아이디
     * @param nickname 변경할 회원 별명
     * @param handicap 변경할 회원 핸디캡
     */
    MemberDto modify(long memberId, String nickname, int handicap);

    /**
     * 회원 정보 조회
     *
     * @param memberId 회원 아이디
     * @return 회원 정보
     */
    Optional<MemberDto> getMember(long memberId);

}

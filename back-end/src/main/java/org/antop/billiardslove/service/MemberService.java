package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Member;

public interface MemberService {
    /**
     * 회원 정보 조회
     *
     * @param memberId 회원 아이디
     * @return 회원 정보
     * @throws org.antop.billiardslove.exception.MemberNotFountException 회원을 찾을 수 없을 경우
     */
    Member getMember(long memberId);
}

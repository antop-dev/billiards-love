package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Member;

public interface MemberService {
    /**
     * 회원 정보 조회
     *
     * @param id 회원 아이디
     * @return 회원 정보
     */
    Member getMember(long id);
}

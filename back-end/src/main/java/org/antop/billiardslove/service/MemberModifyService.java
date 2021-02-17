package org.antop.billiardslove.service;

/**
 * 회원 정보 수정 서비스
 *
 * @author antop
 */
public interface MemberModifyService {

    /**
     * 회원 정보를 수정한다.
     *
     * @param id       회원 아이디
     * @param nickname 변경할 회원 별명
     * @param handicap 변경할 회원 핸디캡
     */
    void modify(long id, String nickname, int handicap);

}

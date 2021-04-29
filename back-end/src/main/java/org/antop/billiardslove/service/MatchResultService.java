package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Match;

/**
 * 경기 결과 서비스
 *
 * @author antop
 */
public interface MatchResultService {

    /**
     * 경기 결과 입력
     *
     * @param matchId  경기 결과 아이디
     * @param memberId 회원 아이디
     * @param results  입력한 결과
     */
    void enter(long matchId, long memberId, Match.Result[] results);

}

package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.MatchDto;

import java.util.List;

/**
 * 경기 조회 관련 서비스
 *
 * @author antop
 */
public interface MatchGetService {

    /**
     * 대회에서 나(회원)를 기준으로 상대방 대진표를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 경기 목록 정보
     */
    List<MatchDto> getMatches(long contestId, long memberId);

    /**
     * 경기 정보를 조회한다.
     *
     * @param matchId  매칭 아이디
     * @param memberId 회원 아이디
     * @return 매칭 정보
     */
    MatchDto getMatch(long matchId, long memberId);

}

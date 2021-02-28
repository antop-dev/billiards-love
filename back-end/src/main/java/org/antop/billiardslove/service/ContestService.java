package org.antop.billiardslove.service;

import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Player;

import java.util.List;

public interface ContestService {
    /**
     * 대회 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 정보
     */
    Contest getContest(long id);

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    List<Contest> getAllContests();

    /**
     * 대회 순위 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 참여 플레이어
     */
    List<Player> getRanks(long id);

    /**
     * 대회 참가
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @throws CantParticipationException    대회 접수를 할 수 없는 상태인 경우
     * @throws AlreadyParticipationException 이미 참여한 대회인 경우
     */
    void participate(long contestId, long memberId, int handicap) throws CantParticipationException, AlreadyParticipationException;
}

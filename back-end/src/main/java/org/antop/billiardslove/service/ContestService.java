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

}

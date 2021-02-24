package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.ContestRankDto;

import java.util.List;

public interface ContestService {
    /**
     * 대회 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 정보
     */
    ContestDto getContest(long id);

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    List<ContestDto> getAllContests();

    /**
     * 대회 순위 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 참여 플레이어
     */
    List<ContestRankDto> getRanks(long id);

    /**
     * 댜회 참가
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 참가 가능 체크
     */
    String getParticipationCheck(long contestId, long memberId);
}

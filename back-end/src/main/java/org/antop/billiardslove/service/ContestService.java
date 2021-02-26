package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.ContestRankDto;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.CantParticipationException;

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
     * 대회 참가
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @throws CantParticipationException    대회 접수를 할 수 없는 상태인 경우
     * @throws AlreadyParticipationException 이미 참여한 대회인 경우
     */
    void participate(long contestId, long memberId, int handicap) throws CantParticipationException, AlreadyParticipationException;
}

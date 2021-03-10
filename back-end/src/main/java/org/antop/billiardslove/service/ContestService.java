package org.antop.billiardslove.service;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.jpa.entity.Contest;

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
     * 대회 참가<br>
     * 회원의 핸디캡과 대회 참가할 때 핸디캡은 다를 수 있다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @param handicap  참가 핸디캡
     */
    void participate(long contestId, long memberId, int handicap);

    /**
     * 대회 개최(?)
     *
     * @param contestId 대회 아이디
     * @return 수정된 대회 정보
     * @throws org.antop.billiardslove.exception.ContestNotFoundException 대회를 찾을 수 없을 경우
     */
    Contest open(long contestId);

    /**
     * 대회 등록
     *
     * @param contestDto 입력된 대회 정보
     * @return 등록된 대회 정보
     */
    Contest register(ContestDto contestDto);

    /**
     * 대회 시작
     *
     * @param contestId 대회 아이디
     */
    void start(long contestId);
}

package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dao.ContestDao;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.AlreadyContestEndException;
import org.antop.billiardslove.exception.AlreadyContestProgressException;
import org.antop.billiardslove.exception.CanNotCancelJoinException;
import org.antop.billiardslove.exception.CanNotJoinContestStateException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ContestService {
    private final ContestDao contestDao;
    private final PlayerService playerService;

    /**
     * 대회 정보 조회
     *
     * @param contestId 대회 아이디
     * @return 대회 정보
     */
    public Optional<ContestDto> getContest(long contestId) {
        return contestDao.findById(contestId).map(this::convert);
    }

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    public List<ContestDto> getAllContests() {
        return contestDao.findAllOrdered()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 대회 참가<br>
     * 회원의 핸디캡과 대회 참가할 때 핸디캡은 다를 수 있다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @param handicap  참가 핸디캡
     */
    @Transactional
    public void join(long contestId, long memberId, int handicap) {
        Contest contest = findContest(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotJoinContestStateException();
        }
        playerService.join(contest.getId(), memberId, handicap);
    }

    /**
     * 대회 개최(?)
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public void open(long contestId) {
        Contest contest = findContest(contestId);
        contest.open();
    }

    /**
     * 대회 등록
     *
     * @param dto 입력된 대회 정보 (아이디 없음)
     * @return 등록된 대회 정보
     */
    @Transactional
    public ContestDto register(ContestDto dto) {
        Contest contest = Contest.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .startTime(dto.getStartTime())
                .endDate(dto.getEndDate())
                .endTime(dto.getEndTime())
                .maxJoiner(dto.getMaxJoiner())
                .build();
        contestDao.save(contest);
        return convert(contest);
    }

    /**
     * 대회 시작
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public void start(long contestId) {
        Contest contest = findContest(contestId);
        contest.start();
        playerService.initPlayers(contest.getId());
    }

    /**
     * 대회 정보 수정
     *
     * @param dto 수정 정보 (아이디 있음)
     * @return 수정된 대회 정보
     */
    @Transactional
    public ContestDto modify(long contestId, ContestDto dto) {
        Contest contest = contestDao.findById(contestId)
                .orElseThrow(ContestNotFoundException::new);
        // 준비중, 접수중, 중지 상태에서만 변경 가능
        if (contest.getState() == Contest.State.PROCEEDING) {
            throw new AlreadyContestProgressException();
        } else if (contest.getState() == Contest.State.END) {
            throw new AlreadyContestEndException();
        }

        contest.setTitle(dto.getTitle());
        contest.setDescription(dto.getDescription());
        contest.setStartDate(dto.getStartDate());
        contest.setStartTime(dto.getStartTime());
        contest.setEndDate(dto.getEndDate());
        contest.setEndTime(dto.getEndTime());
        contest.setMaxJoiner(dto.getMaxJoiner());

        return convert(contest);
    }

    /**
     * 대회 중지
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public void stop(long contestId) {
        Contest contest = findContest(contestId);
        contest.stop();
    }

    /**
     * 대회 종료
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public void end(long contestId) {
        Contest contest = findContest(contestId);
        contest.end();
    }

    /**
     * 대회 참가를 취소한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회워 아이디
     */
    @Transactional
    public void cancelJoin(long contestId, long memberId) {
        Contest contest = findContest(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotCancelJoinException();
        }

        playerService.getPlayer(contestId, memberId)
                .ifPresent(player -> playerService.remove(player.getId()));
    }

    public Contest findContest(long contestId) {
        return contestDao.findById(contestId).orElseThrow(ContestNotFoundException::new);
    }

    private ContestDto convert(Contest contest) {
        long memberId = SecurityUtils.getMemberId();

        return ContestDto.builder()
                .id(contest.getId())
                .title(contest.getTitle())
                .description(contest.getDescription())
                .startDate(contest.getStartDate())
                .startTime(contest.getStartTime())
                .endDate(contest.getEndDate())
                .endTime(contest.getEndTime())
                .maxJoiner(contest.getMaxJoiner())
                .stateCode(contest.getState().getCode())
                .stateName(contest.getState().name())
                .player(playerService.getPlayer(contest.getId(), memberId).orElse(null))
                .build();
    }

}

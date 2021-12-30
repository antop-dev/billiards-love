package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dao.ContestDao;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dao.PlayerDao;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.AlreadyJoinException;
import org.antop.billiardslove.exception.CanNotCancelJoinException;
import org.antop.billiardslove.exception.CanNotJoinContestStateException;
import org.antop.billiardslove.exception.ContestEndException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.ContestProceedingException;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.mapper.ContestMapper;
import org.antop.billiardslove.model.ContestState;
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
    private final PlayerDao playerDao;
    private final MatchDao matchDao;
    private final MemberDao memberDao;
    private final ContestMapper contestMapper;

    /**
     * 대회 정보 조회
     *
     * @param contestId 대회 아이디
     * @return 대회 정보
     */
    public Optional<ContestDto> getContest(long contestId) {
        return contestDao.findById(contestId)
                .map(contest -> contestMapper.toDto(contest, SecurityUtils.getMemberId()));
    }

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    public List<ContestDto> getAllContests() {
        return contestDao.findAllOrdered()
                .stream()
                .map(contest -> contestMapper.toDto(contest, SecurityUtils.getMemberId()))
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
    public ContestDto join(long contestId, long memberId, int handicap) {
        // 대회
        Contest contest = findById(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotJoinContestStateException();
        }

        // 이미 참가한 회원인지 확인
        playerDao.findByContestAndMember(contestId, memberId).ifPresent(player -> {
            throw new AlreadyJoinException();
        });

        // 회원
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Player player = Player.builder()
                .contest(contest)
                .member(member)
                .handicap(handicap)
                .build();
        playerDao.save(player);

        contest.incrementJoiner();

        return contestMapper.toDto(contest, memberId);
    }

    /**
     * 대회 개최(?)
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public ContestDto open(long contestId) {
        Contest contest = findById(contestId);
        contest.open();
        return contestMapper.toDto(contest);
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
        return contestMapper.toDto(contest);
    }

    /**
     * 대회 시작
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public ContestDto start(long contestId) {
        Contest contest = findById(contestId);
        ContestState oldState = contest.getState();
        contest.start();

        // 접수중 상태에서 시작 되었을 때만 선수들을 초기화한다.
        if (oldState == ContestState.ACCEPTING) {
            List<Player> players = playerDao.findByContest(contest.getId());
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                p.assignNumber(i + 1);

                // 대진표 생성
                for (int j = i + 1; j < players.size(); j++) {
                    Player opponent = players.get(j);

                    Match match = Match.builder()
                            .contest(contest)
                            .player1(p)
                            .player2(opponent)
                            .build();

                    matchDao.save(match);
                }
            }
        }

        return contestMapper.toDto(contest);
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
        if (contest.getState() == ContestState.PROCEEDING) {
            throw new ContestProceedingException();
        } else if (contest.getState() == ContestState.END) {
            throw new ContestEndException();
        }

        contest.setTitle(dto.getTitle());
        contest.setDescription(dto.getDescription());
        contest.setStartDate(dto.getStartDate());
        contest.setStartTime(dto.getStartTime());
        contest.setEndDate(dto.getEndDate());
        contest.setEndTime(dto.getEndTime());
        contest.setMaxJoiner(dto.getMaxJoiner());

        return contestMapper.toDto(contest);
    }

    /**
     * 대회 중지
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public ContestDto stop(long contestId) {
        Contest contest = findById(contestId);
        contest.stop();

        return contestMapper.toDto(contest);
    }

    /**
     * 대회 종료
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public ContestDto end(long contestId) {
        Contest contest = findById(contestId);
        contest.end();

        return contestMapper.toDto(contest);
    }

    /**
     * 대회 참가를 취소한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회워 아이디
     */
    @Transactional
    public ContestDto cancelJoin(long contestId, long memberId) {
        Contest contest = findById(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotCancelJoinException();
        }
        playerDao.findByContestAndMember(contestId, memberId)
                .ifPresent(player -> playerDao.remove(player.getId()));

        return contestMapper.toDto(contest);
    }

    public Contest findById(long contestId) {
        return contestDao.findById(contestId).orElseThrow(ContestNotFoundException::new);
    }

}

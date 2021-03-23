package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.AlreadyContestEndException;
import org.antop.billiardslove.exception.AlreadyContestProgressException;
import org.antop.billiardslove.exception.CanNotCancelJoinException;
import org.antop.billiardslove.exception.CanNotJoinContestStateException;
import org.antop.billiardslove.exception.CantEndContestStateException;
import org.antop.billiardslove.exception.CantStartContestStateException;
import org.antop.billiardslove.exception.CantStopContestStateException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Contest.State;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.antop.billiardslove.jpa.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final MemberService memberService;
    private final MatchRepository matchRepository;

    @Override
    public Contest getContest(long id) {
        return contestRepository.findByIdWithFetch(id).orElseThrow(ContestNotFoundException::new);
    }

    @Override
    public List<Contest> getAllContests() {
        return contestRepository.findAllOrdered();
    }

    @Transactional
    @Override
    public void join(long contestId, long memberId, int handicap) {
        Contest contest = getContest(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotJoinContestStateException();
        }

        Member member = memberService.getMember(memberId);
        contest.join(member, handicap);
    }

    @Transactional
    @Override
    public Contest open(long contestId) {
        Contest contest = getContest(contestId);
        contest.setState(State.ACCEPTING);
        return contest;
    }

    @Override
    public Contest register(ContestDto contestDto) {
        Contest contest = Contest.builder()
                .title(contestDto.getTitle())
                .description(contestDto.getDescription())
                .startDate(contestDto.getStartDate())
                .startTime(contestDto.getStartTime())
                .endDate(contestDto.getEndDate())
                .endTime(contestDto.getEndTime())
                .maxJoiner(contestDto.getMaxJoiner())
                .build();
        return contestRepository.save(contest);
    }

    @Transactional
    @Override
    public void start(long contestId) {
        Contest contest = getContest(contestId);
        if (!contest.canStart()) {
            throw new CantStartContestStateException();
        }
        contest.setState(State.PROCEEDING);

        List<Player> players = contest.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player me = players.get(i);
            me.setScore(0);
            me.setNumber(i + 1);
            me.setRank(i + 1);

            for (int j = i + 1; j < players.size(); j++) {
                Player opponent = players.get(j);

                Match match = Match.builder()
                        .contest(contest)
                        .player1(me)
                        .player2(opponent)
                        .build();

                matchRepository.save(match);
            }
        }

    }

    @Override
    public Contest modify(long contestId, ContestDto contestDto) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);

        // 준비중, 접수중, 중지 상태에서만 변경 가능
        if (contest.getState().name().equals("PROCEEDING")) {
            throw new AlreadyContestProgressException();
        } else if (contest.getState().name().equals("END")) {
            throw new AlreadyContestEndException();
        }
        contest.setTitle(contestDto.getTitle());
        contest.setDescription(contestDto.getDescription());
        contest.setStartDate(contestDto.getStartDate());
        contest.setStartTime(contestDto.getStartTime());
        contest.setEndDate(contestDto.getEndDate());
        contest.setEndTime(contestDto.getEndTime());
        contest.setMaxJoiner(contestDto.getMaxJoiner());

        return contest;
    }

    @Transactional
    @Override
    public void stop(long contestId) {
        Contest contest = getContest(contestId);
        if (!contest.canStop()) {
            throw new CantStopContestStateException();
        }
        contest.setState(Contest.State.STOPPED);
    }

    @Transactional
    @Override
    public void end(long contestId) {
        Contest contest = getContest(contestId);
        if (contest.isEnd()) {
            throw new AlreadyContestEndException();
        }
        if (!contest.canEnd()) {
            throw new CantEndContestStateException();
        }

        contest.setState(State.END);
    }

    @Transactional
    @Override
    public void cancelJoin(long contestId, long memberId) {
        Contest contest = getContest(contestId);
        if (!contest.isAccepting()) {
            throw new CanNotCancelJoinException();
        }
        Member member = memberService.getMember(memberId);

        Player player = contest.getPlayer(member);
        contest.removePlayer(player);
    }

}

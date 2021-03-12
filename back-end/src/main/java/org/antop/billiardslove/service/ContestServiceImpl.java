package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.CantParticipateContestStateException;
import org.antop.billiardslove.exception.CantStartContestStateException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final MemberService memberService;
    private final MatchSaveService matchSaveService;

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
    public void participate(long contestId, long memberId, int handicap) {
        Contest contest = getContest(contestId);
        if (!contest.isAccepting()) {
            throw new CantParticipateContestStateException();
        }

        Member member = memberService.getMember(memberId);
        contest.participate(member, handicap);
    }

    @Transactional
    @Override
    public Contest open(long contestId) {
        Contest contest = getContest(contestId);
        contest.setState(Contest.State.ACCEPTING);
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
                .maximumParticipants(contestDto.getMaximumParticipants())
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
        contest.setState(Contest.State.PROCEEDING);

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

                matchSaveService.save(match);
            }
        }

    }

}

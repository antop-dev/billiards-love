package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.CantParticipationException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.antop.billiardslove.jpa.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final PlayerRepository playerRepository;
    private final MemberService memberService;
    private final PlayerService playerService;

    @Override
    public Contest getContest(long id) {
        return contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);
    }

    @Override
    public List<Contest> getAllContests() {
        return contestRepository.findAllOrdered();
    }

    @Override
    public List<Player> getRanks(long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);
        return playerRepository.findByContestOrderByRankAsc(contest);
    }

    @Override
    public void participate(long contestId, long memberId, int handicap) throws CantParticipationException, AlreadyParticipationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        if (!contest.isAccepting()) {
            throw new CantParticipationException();
        }
        Member member = memberService.getMember(memberId);
        Player player = playerService.getPlayer(memberId);

        if (player != null) {
            throw new AlreadyParticipationException();
        }
        playerRepository.save(Player.builder()
                .contest(contest)
                .member(member)
                .handicap(handicap)
                .build());
    }

}

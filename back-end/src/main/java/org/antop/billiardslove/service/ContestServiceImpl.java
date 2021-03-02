package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.exception.CantParticipateContestStateException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
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

}

package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchSaveService, MatchGetService {
    private final ContestService contestService;
    private final MemberService memberService;
    private final MatchRepository matchRepository;

    @Transactional
    @Override
    public void save(Match match) {
        matchRepository.save(match);
    }

    @Override
    public MyMatch getMatches(long contestId, long memberId) {
        Contest contest = contestService.getContest(contestId);
        Member member = memberService.getMember(memberId);

        Player player = contest.getPlayer(member);
        List<Match> matches = matchRepository.findJoinedIn(contest, member);

        return MyMatch.builder()
                .player(player)
                .matches(matches)
                .build();
    }

}

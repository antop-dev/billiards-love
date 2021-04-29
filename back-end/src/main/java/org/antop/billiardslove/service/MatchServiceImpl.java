package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.NotJoinedMatchException;
import org.antop.billiardslove.exception.PlayerNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchSaveService, MatchGetService, MatchResultService {
    private final ContestService contestService;
    private final MemberService memberService;
    private final MatchRepository matchRepository;

    @Transactional
    @Override
    public void save(Match match) {
        matchRepository.save(match);
    }

    @Override
    public List<MatchDto> getMatches(long contestId, long memberId) {
        // 대회 정보
        Contest contest = contestService.getContest(contestId);
        // 회원 정보
        Member member = memberService.getMember(memberId);
        // 선수(나) 정보
        final Player player = contest.getPlayer(member);
        // 해당 대회의 매칭 목록
        List<Match> matches = matchRepository.findJoinedIn(contest, member);
        return matches.stream()
                .map(it -> convert(it, player))
                .sorted(Comparator.comparingLong(o -> o.getOpponent().getNumber()))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public MatchDto getMatch(long matchId, long memberId) {
        Match match = matchRepository.findById(matchId).orElseThrow(MatchNotFoundException::new);
        // 회원 정보
        Member member = memberService.getMember(memberId);
        // 선수(나) 정보
        final Player player = match.getContest().getPlayer(member);
        return convert(match, player);
    }

    private MatchDto convert(Match match, Player player) {
        // 상대 선수
        Player opponent = match.getOpponent(player);
        return MatchDto.builder()
                .id(match.getId())
                .result(match.getMatchResult(player).toArray())
                .opponent(MatchDto.Opponent.builder()
                        .id(opponent.getId())
                        .number(opponent.getNumber())
                        .nickname(opponent.getMember().getNickname())
                        .build())
                .closed(match.isConfirmed())
                .build();
    }

    @Transactional
    @Override
    public void enter(long matchId, long memberId, Match.Result[] results) {
        Match match = matchRepository.findById(matchId).orElseThrow(MatchNotFoundException::new);
        Member member = memberService.getMember(memberId);
        try {
            Player player = match.getContest().getPlayer(member);
            match.enterResult(player, results[0], results[1], results[2]);
        } catch (PlayerNotFoundException e) {
            throw new NotJoinedMatchException();
        }
    }
}

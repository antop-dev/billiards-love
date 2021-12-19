package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dao.PlayerDao;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchClosedException;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.mapper.MatchMapper;
import org.antop.billiardslove.model.Outcome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 경기 서비스
 *
 * @author antop
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchService {
    private final MemberDao memberDao;
    private final MatchDao matchDao;
    private final MatchMapper matchMapper;
    private final PlayerDao playerDao;

    /**
     * 대회에서 나(회원)를 기준으로 상대방 대진표를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 경기 목록 정보
     */
    public List<MatchDto> getMatches(long contestId, long memberId) {
        final Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        // 해당 대회의 매칭 목록
        List<Match> matches = matchDao.findJoinedIn(contestId, memberId);
        return matches.stream()
                .map(it -> matchMapper.toDto(it, member))
                .sorted(Comparator.comparingLong(o -> o.getRight().getNumber()))
                .collect(Collectors.toList());
    }

    /**
     * 경기 정보를 조회한다.
     *
     * @param matchId  매칭 아이디
     * @param memberId 회원 아이디
     * @return 매칭 정보
     */
    public Optional<MatchDto> getMatch(long matchId, long memberId) {
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return matchDao.findById(matchId).map(match -> matchMapper.toDto(match, member));
    }

    /**
     * 경기 결과 입력
     *
     * @param matchId  경기 결과 아이디
     * @param memberId 회원 아이디
     * @param results  입력한 결과
     */
    @Transactional
    public MatchDto enter(long matchId, long memberId, Outcome[] results) {
        Match match = matchDao.findById(matchId).orElseThrow(MatchNotFoundException::new);
        if (match.isConfirmed()) {
            throw new MatchClosedException();
        }
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        match.enterResult(member.getId(), results[0], results[1], results[2]);
        return matchMapper.toDto(match, member);
    }

    /**
     * 경기를 확정한다.
     *
     * @param matchId   경기 아이디
     * @param managerId 확정 짓는 회원(관리자) 아이디
     * @param left      왼쪽 선수 결과
     * @param right     오른쪽 선수 결과
     * @return 경기 정보
     */
    @Transactional
    public MatchDto decide(long matchId, long managerId, Outcome[] left, Outcome[] right) {
        Match match = matchDao.findById(matchId).orElseThrow(MatchNotFoundException::new);
        Member manager = memberDao.findById(managerId).orElseThrow(MemberNotFoundException::new);
        match.decide(manager, left, right);
        matchDao.saveAndFlush(match); // 바로 저장
        computeRank(match.getContest()); // 순위 재계산
        return matchMapper.toDto(match, manager);
    }

    /**
     * 해당 대회의 순위를 재계산한다.
     *
     * @param contest 대회 정보
     */
    @Transactional
    public void computeRank(Contest contest) {
        val matches = playerDao.findByContest(contest.getId());
        // 선수들의 점수(score)로 내림차순 정렬
        val players = matches.stream()
                .sorted((o1, o2) -> o2.getScore() - o1.getScore())
                .collect(Collectors.toList());
        // 순위(rank)는 동률이 있다.
        int rank = 0;
        int count = 1;
        long score = Long.MAX_VALUE;
        for (Player player : players) {
            if (player.getScore() < score) {
                rank++;
                if (rank != count) {
                    rank = count;
                }
            }
            player.setRank(rank);
            score = player.getScore();
            count++;
        }
    }

}

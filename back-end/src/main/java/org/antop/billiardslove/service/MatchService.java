package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
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
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Match match = matchDao.findById(matchId).orElseThrow(MatchNotFoundException::new);
        match.enterResult(member.getId(), results[0], results[1], results[2]);
        return matchMapper.toDto(match, member);
    }

    /**
     * 대진표를 저장한다.
     *
     * @param match {@link Match} 대진표 정보
     */
    @Transactional
    public void save(Match match) {
        matchDao.save(match);
    }

    /**
     * 경기를 확정한다.
     *
     * @param matchId  경기 아이디
     * @param memberId 확정 짓는 회원(관리자) 아이디
     * @param left     왼쪽 선수 결과
     * @param right    오른쪽 선수 결과
     * @return 경기 정보
     */
    public MatchDto decide(long matchId, long memberId, Outcome[] left, Outcome[] right) {
        Match match = matchDao.findById(matchId).orElseThrow(MatchNotFoundException::new);
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFoundException::new);
        match.decide(member, left, right);
        return matchMapper.toDto(match, member);
    }

}

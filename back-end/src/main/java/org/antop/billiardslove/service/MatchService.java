package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.jpa.entity.Match;
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
        // 해당 대회의 매칭 목록
        List<Match> matches = matchDao.findJoinedIn(contestId, memberId);
        return matches.stream()
                .map(it -> matchMapper.toDto(it, memberId))
                .sorted(Comparator.comparingLong(o -> o.getOpponent().getNumber()))
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
        return matchDao.findById(matchId).map(match -> matchMapper.toDto(match, memberId));
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
        match.enterResult(memberId, results[0], results[1], results[2]);
        return matchMapper.toDto(match, memberId);
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

}

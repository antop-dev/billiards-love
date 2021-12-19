package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.QContest;
import org.antop.billiardslove.jpa.entity.QMember;
import org.antop.billiardslove.jpa.entity.QPlayer;
import org.antop.billiardslove.jpa.repository.MatchRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QMatch.match;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class MatchDao extends QuerydslRepositorySupport {
    private final MatchRepository repository;

    public MatchDao(MatchRepository repository) {
        super(Match.class);
        this.repository = repository;
    }

    public Optional<Match> findById(long matchId) {
        return repository.findById(matchId);
    }

    @Transactional
    public void save(Match match) {
        repository.save(match);
    }

    /**
     * 내가 참가한 대회의 경기를 나를 기준으로 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 경기 목록
     */
    public List<Match> findJoinedIn(long contestId, long memberId) {
        QPlayer player1 = new QPlayer("p1");
        QPlayer player2 = new QPlayer("p2");

        QMember member1 = new QMember("m1");
        QMember member2 = new QMember("m2");

        return from(match)
                .join(match.contest, QContest.contest)
                .fetchJoin()
                .join(match.player1, player1)
                .fetchJoin()
                .join(player1.member, member1)
                .fetchJoin()
                .join(match.player2, player2)
                .fetchJoin()
                .join(player2.member, member2)
                .fetchJoin()
                .where(match.contest.id.eq(contestId)
                        .and(member1.id.eq(memberId).or(member2.id.eq(memberId)))
                )
                .fetch();
    }

    /**
     * 경기 정보를 저장한다.<br>
     * JPA 라이프사이클에 의해 나중에 쿼리가 수행되지 않고 바로 수행된다.
     *
     * @param match 경기 정보
     */
    @Transactional
    public void saveAndFlush(Match match) {
        repository.saveAndFlush(match);
    }
}

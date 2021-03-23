package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.QContest;
import org.antop.billiardslove.jpa.entity.QMember;
import org.antop.billiardslove.jpa.entity.QPlayer;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static org.antop.billiardslove.jpa.entity.QMatch.match;

public class MatchRepositoryImpl extends QuerydslRepositorySupport implements MatchRepositoryCustom {

    public MatchRepositoryImpl() {
        super(Match.class);
    }

    @Override
    public List<Match> findJoinedIn(Contest contest, Member member) {
        QPlayer player1 =new QPlayer("p1");
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
                .where(match.contest.eq(contest)
                        .and(member1.eq(member).or(member2.eq(member)))
                )
                .fetch();
    }
}

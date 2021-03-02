package org.antop.billiardslove.jpa.repository;

import com.querydsl.jpa.JPQLQuery;
import org.antop.billiardslove.jpa.entity.Contest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QContest.contest;
import static org.antop.billiardslove.jpa.entity.QPlayer.player;

public class ContestRepositoryImpl extends QuerydslRepositorySupport implements ContestRepositoryCustom {

    public ContestRepositoryImpl() {
        super(Contest.class);
    }

    @Override
    public Optional<Contest> findByIdWithFetch(long contestId) {
        JPQLQuery<Contest> query = from(contest)
                .leftJoin(contest.players, player)
                .fetchJoin()
                .where(contest.id.eq(contestId));
        return Optional.ofNullable(query.fetchOne());
    }

    @Override
    public List<Contest> findAllOrdered() {
        List<Contest> fetch = from(contest)
                .leftJoin(contest.players, player)
                .fetchJoin()
                .orderBy(contest.state.asc(),
                        // NULL을 정렬에서 아래로 보낸다.
                        contest.startDate.coalesce(LocalDate.of(9999, 12, 31)).asc(),
                        contest.startTime.coalesce(LocalTime.of(23, 59, 59, 999999)).asc(),
                        contest.created.desc())
                .fetch();
        return new ArrayList<>(new LinkedHashSet<>(fetch));
    }

}

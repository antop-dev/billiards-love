package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.antop.billiardslove.jpa.entity.QContest.contest;

public class ContestRepositoryImpl extends QuerydslRepositorySupport implements ContestRepositoryCustom {

    public ContestRepositoryImpl() {
        super(Contest.class);
    }

    @Override
    public List<Contest> findAllOrdered() {
        List<Contest> fetch = from(contest)
                .orderBy(contest.state.asc(),
                        // NULL을 정렬에서 아래로 보낸다.
                        contest.startDate.coalesce(LocalDate.of(9999, 12, 31)).asc(),
                        contest.startTime.coalesce(LocalTime.of(23, 59, 59, 999999)).asc(),
                        contest.created.desc())
                .fetch();
        return new ArrayList<>(new LinkedHashSet<>(fetch));
    }

}

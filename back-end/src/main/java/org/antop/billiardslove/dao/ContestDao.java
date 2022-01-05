package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QContest.contest;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class ContestDao extends QuerydslRepositorySupport {
    private final ContestRepository repository;

    public ContestDao(ContestRepository repository) {
        super(Contest.class);
        this.repository = repository;
    }

    /**
     * 대회 정보 조회
     *
     * @param contestId 대회 아이디
     * @return 대회 엔티티
     */
    public Optional<Contest> findById(long contestId) {
        return repository.findById(contestId);
    }

    /**
     * 업데이트를 하려는 락을 건 대회 정보 조회<br>
     * 이 메서드를 이용해서 대회 정보를 조회시 다른 트랜잭션은 이 데이터를 조회한 트랜잭션이 끝날때까지 기다린다.
     *
     * @param contestId 대회 아이디
     * @return 대회 엔티티
     */
    public Optional<Contest> findByIdForUpdate(long contestId) {
        return repository.findByIdForUpdate(contestId);
    }

    /**
     * 정렬하여 조회한다.
     *
     * @return 대회 목록
     */
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

    @Transactional
    public Contest save(Contest contest) {
        return repository.save(contest);
    }

}

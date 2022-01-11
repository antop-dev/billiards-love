package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QContest.contest;
import static org.antop.billiardslove.model.ContestState.PREPARING;
import static org.antop.billiardslove.model.ContestState.STOPPED;

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
     * 회원을 위한 대회 목록을 조회한다.<br>
     * 준비중, 종료
     *
     * @return 대회 목록
     */
    public List<Contest> findListForMember() {
        return from(contest)
                .where(contest.state.notIn(PREPARING, STOPPED))
                .orderBy(contest.id.desc())
                .fetch();
    }

    /**
     * 관리자를 위한 모든 대회 목록 조회
     *
     * @return 대회 목록
     */
    public List<Contest> findListForManager() {
        return from(contest)
                .orderBy(contest.id.desc())
                .fetch();
    }

    @Transactional
    public Contest save(Contest contest) {
        return repository.save(contest);
    }

}

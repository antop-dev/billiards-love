package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.DataJpaBase;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ContestRepositoryTest extends DataJpaBase {
    @Autowired
    private ContestRepository repository;

    @Test
    @DisplayName("대회 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        final Contest contest = contest();
        repository.save(contest);
        assertThat(contest.getId(), notNullValue());

        flushAndClear();

        // 2. 실행
        Optional<Contest> optional = repository.findById(contest.getId());
        // 3. 검증
        assertThat(optional, isPresent());
        optional.ifPresent(it -> {
            assertThat(it.getTitle(), is(contest.getTitle()));
            assertThat(it.getDescription(), is(contest.getDescription()));
            assertThat(it.getStartDate(), is(contest.getStartDate()));
            assertThat(it.getStartTime(), is(contest.getStartTime()));
            assertThat(it.getEndDate(), is(contest.getEndDate()));
            assertThat(it.getEndTime(), is(contest.getEndTime()));
            assertThat(it.getState(), is(ContestState.PREPARING));
            assertThat(it.getMaxJoiner(), is(contest.getMaxJoiner()));
        });
    }

    @Test
    @DisplayName("없는 대회를 조회한다.")
    void notExist() {
        // 1. 데이터 준비
        Contest contest = contest();
        repository.save(contest);
        flushAndClear();

        // 2. 실행
        repository.deleteById(contest.getId());
        flushAndClear();

        // 3. 검증
        Optional<Contest> optional = repository.findById(contest.getId());
        assertThat(optional, isEmpty());
    }

}

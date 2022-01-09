package org.antop.billiardslove.dao;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class ContestDaoTest extends SpringBootBase {
    @Autowired
    private ContestDao dao;

    @Test
    void findById() {
        Optional<Contest> optional = dao.findById(1L);
        assertThat(optional, isPresent());

        optional.ifPresent(contest -> {
            assertThat(contest.getTitle(), is("2021 리그전"));
            assertThat(contest.getDescription(), is("2021.01.01~"));
            assertThat(contest.getStartDate(), is(LocalDate.of(2021, 1, 1)));
            assertThat(contest.getStartTime(), is(LocalTime.of(0, 0, 0)));
            assertThat(contest.getEndDate(), is(LocalDate.of(2021, 12, 30)));
            assertThat(contest.getEndTime(), is(LocalTime.of(23, 59, 59)));
            assertThat(contest.getState(), is(ContestState.PROCEEDING));
            assertThat(contest.getMaxJoiner(), is(32));
            assertThat(contest.getCreated(), is(LocalDateTime.of(2019, 11, 12, 15, 11, 45)));
            assertThat(contest.getModified(), nullValue());
        });
    }

    @Test
    void save() {
        Contest contest = Contest.builder()
                .title("코로나 리그전 2020")
                .build();
        dao.save(contest);
        assertThat(contest.getId(), notNullValue());
    }

}

package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Contest.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class ContestRepositoryTest extends SpringBootBase {
    @Autowired
    private ContestRepository repository;

    @Test
    void findById() {
        Optional<Contest> optional = repository.findById(1L);
        assertThat(optional, isPresent());

        optional.ifPresent(contest -> {
            assertThat(contest.getTitle(), is("2021 리그전"));
            assertThat(contest.getDescription(), is("2021.01.01~"));
            assertThat(contest.getStartDate(), is(LocalDate.of(2021, 1, 1)));
            assertThat(contest.getStartTime(), is(LocalTime.of(0, 0, 0)));
            assertThat(contest.getEndDate(), is(LocalDate.of(2021, 12, 30)));
            assertThat(contest.getEndTime(), is(LocalTime.of(23, 59, 59)));
            assertThat(contest.getState(), is(State.PROCEEDING));
            assertThat(contest.getMaximumParticipants(), is(32));
            assertThat(contest.getCreated(), is(LocalDateTime.of(2019, 11, 12, 15, 11, 45)));
            assertThat(contest.getModified(), nullValue());
        });
    }

    @Test
    void save() {
        Contest contest = Contest.builder()
                .title("코로나 리그전 2020")
                .build();
        repository.save(contest);
        assertThat(contest.getId(), notNullValue());
    }

    @Test
    void change() {
        Optional<Contest> optional = repository.findById(2L);
        assertThat(optional, isPresent());

        optional.ifPresent(contest -> {
            contest.setTitle("2020 리그전 - 2");
            contest.setDescription("2020년 2번째 리그전 코로나로 인해 종료");
            contest.setState(State.END);

            flush();
            assertThat(contest.getModified(), notNullValue());
        });
    }

    @Test
    void findAllWithPlayers() {
        List<Contest> contests = repository.findAllOrdered();
        // 시작일이 입력된 "준비중인 대회 (2)"가 "준비중인 대회 (1)"보다 위에 있어야 한다.
        assertThat(contests.get(2).getId(), is(4L));
        assertThat(contests.get(2).getTitle(), is("준비중인 대회 (2)"));
        assertThat(contests.get(3).getId(), is(3L));
        assertThat(contests.get(3).getTitle(), is("준비중인 대회 (1)"));
    }
}

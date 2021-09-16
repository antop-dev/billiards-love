package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ContestRepositoryTest extends SpringBootBase {
    @Autowired
    private ContestRepository repository;

    @Test
    @DisplayName("대회 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        repository.save(contestData);
        flushAndClear();

        // 2. 실행
        Optional<Contest> optional = repository.findById(contestData.getId());

        // 3. 검증
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(contest -> {
            assertThat(contest.getTitle(), is("코로나 추석 리그전 2021"));
            assertThat(contest.getDescription(), is("상금 : 갤러시 폴드 3"));
            assertThat(contest.getStartDate(), is(LocalDate.of(2021, 9, 18)));
            assertThat(contest.getStartTime(), is(LocalTime.of(0, 0, 0)));
            assertThat(contest.getEndDate(), is(LocalDate.of(2021, 9, 30)));
            assertThat(contest.getEndTime(), is(LocalTime.of(23, 59, 59)));
            assertThat(contest.getState(), is(ContestState.PREPARING));
            assertThat(contest.getMaxJoiner(), is(16));
        });
    }

    @Test
    @DisplayName("대회정보를 변경한다.")
    void change() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        repository.save(contestData);
        flushAndClear();

        // 2. 실행
        repository.findById(contestData.getId()).ifPresent(contest -> {
            contest.setTitle("코로나 추석 리그전 2021 - 2");
            contest.setDescription("2021년 2번째 리그전 설연휴로 인해 종료");
            contest.end();
            flush();
        });
        flushAndClear();

        // 3. 검증
        Optional<Contest> optional = repository.findById(contestData.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(contest -> {
            assertThat(contest.getModified(), notNullValue());
            assertThat(contest.getTitle(), is("코로나 추석 리그전 2021 - 2"));
            assertThat(contest.getDescription(), is("2021년 2번째 리그전 설연휴로 인해 종료"));
            assertThat(contest.getStartDate(), is(LocalDate.of(2021, 9, 18)));
            assertThat(contest.getStartTime(), is(LocalTime.of(0, 0, 0)));
            assertThat(contest.getEndDate(), is(LocalDate.of(2021, 9, 30)));
            assertThat(contest.getEndTime(), is(LocalTime.of(23, 59, 59)));
            assertThat(contest.getState(), is(ContestState.END));
            assertThat(contest.getMaxJoiner(), is(16));
        });
    }

    @Test
    @DisplayName("없는 대회를 조회한다.")
    void notExist() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        repository.save(contestData);
        flushAndClear();

        // 2. 실행
        repository.deleteById(contestData.getId());
        flushAndClear();

        // 3. 검증
        Assertions.assertThrows(ContestNotFoundException.class, () -> {
            repository.findById(contestData.getId()).orElseThrow(ContestNotFoundException::new);
        });
    }

}

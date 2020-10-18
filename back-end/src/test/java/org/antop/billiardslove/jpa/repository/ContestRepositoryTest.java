package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.ProgressStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("대회 테스트")
@EnableJpaAuditing
class ContestRepositoryTest extends DataJpaTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("대회 데이터를 조회한다")
    void select() {
        Optional<Contest> optional = contestRepository.findById(1L);
        assertThat(optional.isPresent(), is(true));
        Contest contest = optional.get();
        assertThat(contest.getTitle(), is("2019 리그전"));
    }

    @Test
    @DisplayName("대회 데이터를 등록한다.")
    void insert() {
        Optional<Manager> optional = managerRepository.findById(1L);

        Contest contest = Contest.builder()
                .title("코로나 리그전 2020")
                .description("1등 상품 맥북")
                .startDate(LocalDate.of(2020, 12, 25))
                .startTime(LocalTime.of(10, 00))
                .endDate(LocalDate.of(2021, 1, 31))
                .endTime(LocalTime.of(12, 0))
                .maximumParticipants(16)
                .progressStatus(ProgressStatus.NONE)
                .registrationUser(optional.get())
                .build();
        contestRepository.save(contest);
        System.out.println(contest);

        Optional<Contest> optional1 = contestRepository.findById(4L);
        assertThat(optional1.isPresent(), is(true));
        Contest contest1 = optional1.get();
        assertThat(contest1.getTitle(), is("코로나 리그전 2020"));
    }

    @Test
    @DisplayName("대회 데이터를 갱신한다.")
    void J6l1Z() {
        contestRepository.findById(2L).ifPresent(it -> {
            it.setTitle("2020 리그전 - 2");
            it.setDescription("2020년 2번째 리그전 코로나로 인해 종료");
            it.setProgressStatus(ProgressStatus.END);
        });

        Optional<Contest> optional = contestRepository.findById(2L);
        assertThat(optional.isPresent(), is(true));
        Contest contest = optional.get();
        assertThat(contest.getTitle(), is("2020 리그전 - 2"));
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        Optional<Manager> optional = managerRepository.findById(1L);

        Contest contest = Contest.builder()
                .id(3L)
                .title("코로나 리그전 2020")
                .description("1등 상품 맥북")
                .startDate(LocalDate.of(2020, 12, 25))
                .startTime(LocalTime.of(10, 00))
                .endDate(LocalDate.of(2021, 1, 31))
                .endTime(LocalTime.of(12, 0))
                .maximumParticipants(16)
                .progressStatus(ProgressStatus.NONE)
                .registrationUser(optional.get())
                .registerDateTime(LocalDateTime.now())
                .build();
        contestRepository.save(contest);

        Optional<Contest> optional1 = contestRepository.findById(3L);
        assertThat(optional1.isPresent(), is(true));
        Contest contest1 = optional1.get();
        assertThat(contest1.getTitle(), is("코로나 리그전 2020"));
    }
}

package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.ProgressStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("대회 테스트")
@EnableJpaAuditing
class ContestRepositoryTest extends DataJpaTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ContestRepository contestRepository;

    @AfterEach
    void afterEach() {
        contestRepository.deleteAll();
    }

    @Test
    void insert() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Manager manager2 = new Manager();
        manager2.setUsername("admin2");
        manager2.setPassword("pass2");
        managerRepository.save(manager2);

        Contest contest = new Contest();
        contest.setTitle("당구사랑대회");
        contest.setEndDate(LocalDate.now());
        contest.setRegistrationUser(manager1);
        contest.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest);

        assertThat(contest.getId(), notNullValue());
        assertThat(contest.getTitle(), notNullValue());
        assertThat(contest.getDescription(), nullValue());
        assertThat(contest.getStartDate(), nullValue());
        assertThat(contest.getStartTime(), nullValue());
        assertThat(contest.getEndDate(), notNullValue());
        assertThat(contest.getEndTime(), nullValue());
        assertThat(contest.getProgressStatus(), notNullValue());
        assertThat(contest.getMaximumParticipants(), is(0));
        assertThat(contest.getRegistrationUser(), notNullValue());
        assertThat(contest.getRegisterDateTime(), notNullValue());
        assertThat(contest.getModifyUser(), nullValue());
        assertThat(contest.getModifyDateTime(), notNullValue());
    }

    @Test
    void insert_read() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest1 = new Contest();
        contest1.setTitle("당구사랑대회1");
        contest1.setEndDate(LocalDate.now());
        contest1.setRegistrationUser(manager1);
        contest1.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest1);

        Manager manager2 = new Manager();
        manager2.setUsername("admin2");
        manager2.setPassword("pass2");
        managerRepository.save(manager2);

        Contest contest2 = new Contest();
        contest2.setTitle("당구사랑대회2");
        contest2.setEndDate(LocalDate.now());
        contest2.setRegistrationUser(manager1);
        contest2.setProgressStatus(ProgressStatus.PROGRESS);
        contestRepository.save(contest2);

        List<Contest> contestList = contestRepository.findAll();

        assertThat(contestList, hasSize(2));
        assertThat(contestList, contains(contest1, contest2));
    }

    @Test
    void insert_delete() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest1 = new Contest();
        contest1.setTitle("당구사랑대회1");
        contest1.setEndDate(LocalDate.now());
        contest1.setRegistrationUser(manager1);
        contest1.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest1);

        Manager manager2 = new Manager();
        manager2.setUsername("admin2");
        manager2.setPassword("pass2");
        managerRepository.save(manager2);

        Contest contest2 = new Contest();
        contest2.setTitle("당구사랑대회2");
        contest2.setEndDate(LocalDate.now());
        contest2.setRegistrationUser(manager1);
        contest2.setProgressStatus(ProgressStatus.PROGRESS);
        contestRepository.save(contest2);

        contestRepository.deleteAll();

        assertThat(contestRepository.findAll(), IsEmptyCollection.empty());
    }
}

package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.antop.billiardslove.jpa.entity.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("공지사항 테스트")
@EnableJpaAuditing
class NoticeRepositoryTest extends DataJpaTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("공지사항 데이터를 조회한다")
    void select() {
        Optional<Notice> noticeOptional = noticeRepository.findById(1L);
        assertThat(noticeOptional.isPresent(), is(true));
        Notice notice = noticeOptional.get();
        assertThat(notice.getTitle(), is("공지사항 제목"));
    }

    @Test
    @DisplayName("공지사항 데이터를 등록한다.")
    void insert() {
        Optional<Manager> managerOptional = managerRepository.findById(1L);
        Optional<Contest> contestOptional = contestRepository.findById(1L);

        Notice notice = Notice.builder()
                .title("2020리그전 공지사항")
                .contents("참가비는 만원입니다.")
                .contest(contestOptional.get())
                .canSkip(true)
                .registrationUser(managerOptional.get())
                .build();
        noticeRepository.save(notice);

        Optional<Notice> noticeOptional = noticeRepository.findById(2L);
        assertThat(noticeOptional.isPresent(), is(true));
        Notice notice1 = noticeOptional.get();
        assertThat(notice1.getTitle(), is("2020리그전 공지사항"));
    }

    @Test
    @DisplayName("공지사항 데이터를 갱신한다.")
    void J6l1Z() {
        noticeRepository.findById(1L).ifPresent(it -> {
            it.setTitle("2020 리그전 잠정연기");
            it.setContents("코로나로 인해 2020 리그전을 잠정 연기합니다.");
        });

        Optional<Notice> noticeOptional = noticeRepository.findById(1L);
        assertThat(noticeOptional.isPresent(), is(true));
        Notice notice = noticeOptional.get();
        assertThat(notice.getTitle(), is("2020 리그전 잠정연기"));
    }

}

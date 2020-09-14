package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.antop.billiardslove.jpa.entity.Notice;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("공지사항 테스트")
@EnableJpaAuditing
class NoticeRepositoryTest extends DataJpaTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @AfterEach
    void afterEach() {
        noticeRepository.deleteAll();
    }

    @Test
    void insert() {
        Manager manager = new Manager();
        manager.setUsername("jammini@naver.com");
        manager.setPassword("password1");

        managerRepository.save(manager);

        Notice notice = new Notice();
        notice.setTitle("공지사항1");
        notice.setContents("내용은 abcd 입니다~~~!");
        notice.setCanSkip(false);
        notice.setRegistrationUser(manager);

        noticeRepository.save(notice);

        assertThat(notice.getId(), notNullValue());
        assertThat(notice.getTitle(), notNullValue());
        assertThat(notice.getContents(), notNullValue());
        assertThat(notice.isCanSkip(), notNullValue());
        assertThat(notice.getRegistrationUser(), notNullValue());
        assertThat(notice.getRegisterDateTime(), notNullValue());
    }

    @Test
    void insert_read() {
        Manager manager1 = new Manager();
        manager1.setUsername("jammini123@naver.com");
        manager1.setPassword("password1");
        managerRepository.save(manager1);

        Notice notice1 = new Notice();
        notice1.setTitle("공지사항1~!");
        notice1.setContents("내용은 abcd 입니다~~~~!");
        notice1.setCanSkip(false);
        notice1.setRegistrationUser(manager1);
        noticeRepository.save(notice1);

        Manager manager2 = new Manager();
        manager2.setUsername("jm@naver.com");
        manager2.setPassword("p@ssword");
        managerRepository.save(manager2);

        Notice notice2 = new Notice();
        notice2.setTitle("공지사항2 입니다");
        notice2.setContents("내용은 abcdsaasdasdfasdfadsaas 입니다~2");
        notice2.setCanSkip(false);
        notice2.setRegistrationUser(manager2);
        noticeRepository.save(notice2);

        List<Notice> noticeList = noticeRepository.findAll();

        assertThat(noticeList, hasSize(2));
        assertThat(noticeList, contains(notice1, notice2));
    }

    @Test
    void insert_delete() {
        Manager manager1 = new Manager();
        manager1.setUsername("jammini1234@naver.com");
        manager1.setPassword("p@ssword");
        managerRepository.save(manager1);

        Notice notice1 = new Notice();
        notice1.setTitle("공지사항1");
        notice1.setContents("내용은 abcdsaasdasdfasdfadsaas 입니다~2");
        notice1.setCanSkip(false);
        notice1.setRegistrationUser(manager1);
        noticeRepository.save(notice1);

        Manager manager2 = new Manager();
        manager2.setUsername("jm@naver.com");
        manager2.setPassword("p@ssword");
        managerRepository.save(manager2);

        Notice notice2 = new Notice();
        notice2.setTitle("공지사항2");
        notice2.setContents("내용은 abcdsaasdasdfasdfadsaas 입니다~3");
        notice2.setCanSkip(false);
        notice2.setRegistrationUser(manager2);
        noticeRepository.save(notice2);

        noticeRepository.deleteAll();

        assertThat(noticeRepository.findAll(), IsEmptyCollection.empty());
    }
}
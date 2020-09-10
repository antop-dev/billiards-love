package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class ManagerRepositoryTest extends DataJpaTest {
    @Autowired
    private ManagerRepository repository;

    @DisplayName("인서트 했을 떄 아이디가 자동 생성된다.")
    @Test
    void qUqtJ() {
        Manager manager = new Manager();
        manager.setUsername("antop@naver.com");
        manager.setPassword("p@ssword");

        repository.save(manager);

        assertThat(manager.getId(), notNullValue());
    }

}

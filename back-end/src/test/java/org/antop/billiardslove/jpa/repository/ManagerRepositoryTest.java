package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("관리자 테스트")
class ManagerRepositoryTest extends DataJpaTest {
    @Autowired
    private ManagerRepository repository;

    @Test
    void insert() {
        Manager manager = new Manager();
        manager.setUsername("antop@naver.com");
        manager.setPassword("password1");

        repository.save(manager);

        assertThat(manager.getId(), notNullValue());
        assertThat(manager.getUsername(), notNullValue());
        assertThat(manager.getPassword(), notNullValue());
    }

    @Test
    void insert_read() {
        Manager manager1 = new Manager();
        manager1.setUsername("antop@naver.com");
        manager1.setPassword("password1");
        repository.save(manager1);

        Manager manager2 = new Manager();
        manager2.setUsername("jm@naver.com");
        manager2.setPassword("password2");
        repository.save(manager2);

        Manager manager3 = new Manager();
        manager3.setUsername("hi@naver.com");
        manager3.setPassword("password3");
        repository.save(manager3);

        List<Manager> managerList = repository.findAll();

        assertThat(managerList, hasSize(3));
        assertThat(managerList, contains(manager1, manager2, manager3));
    }

    @Test
    void insert_delete() {
        Manager manager1 = new Manager();
        manager1.setUsername("antop@naver.com");
        manager1.setPassword("p@ssword1");
        repository.save(manager1);

        Manager manager2 = new Manager();
        manager2.setUsername("jm@naver.com");
        manager2.setPassword("password2");
        repository.save(manager2);

        Manager manager3 = new Manager();
        manager3.setUsername("hi@naver.com");
        manager3.setPassword("password3");
        repository.save(manager3);

        repository.deleteAll();

        assertThat(repository.findAll(), IsEmptyCollection.empty());
    }

    @Test
    void select() {
        for (Manager manager : repository.findAll()) {
            System.out.println("manager = " + manager);
        }
    }
}

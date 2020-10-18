package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@EnableJpaAuditing
@DisplayName("관리자 테스트")
class ManagerRepositoryTest extends DataJpaTest {
    @Autowired
    private ManagerRepository repository;

    @Test
    @DisplayName("관리자 데이터를 조회한다.")
    void read() {
        Optional<Manager> optional = repository.findById(1L);
        assertThat(optional.isPresent(), is(true));
        Manager manager = optional.get();
        assertThat(manager.getUsername(), is("admin"));
    }

    @Test
    @DisplayName("새로운 관리자 데이터를 등록한다.")
    void E6RA6() {
        Manager manager = Manager.builder()
                .username("manager")
                .password("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS")
                .build();
        repository.save(manager);
        System.out.println(manager);
        Optional<Manager> optional = repository.findById(5L);
        assertThat(optional.isPresent(), is(true));
        Manager manager1 = optional.get();
        assertThat(manager1.getUsername(), is("manager"));
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        Manager manager = Manager.builder()
                .id(1L)
                .username("admin1")
                .password("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS1")
                .build();
        repository.save(manager);
        System.out.println(manager);

        Optional<Manager> optional = repository.findById(1L);
        assertThat(optional.isPresent(), is(true));
        Manager manager1 = optional.get();
        assertThat(manager1.getUsername(), is("admin1"));
        assertThat(manager1.getPassword(), is("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS1"));
    }

    @Test
    void select() {
        for (Manager manager : repository.findAll()) {
            System.out.println("manager = " + manager);
        }
    }
}

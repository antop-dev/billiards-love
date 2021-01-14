package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("관리자 테스트")
class ManagerRepositoryTest extends DataJpaTest {
    @Autowired
    private ManagerRepository repository;

    @Test
    @DisplayName("관리자 데이터를 조회한다.")
    void read() {
        Optional<Manager> managerOptional = repository.findById(1L);
        assertThat(managerOptional.isPresent(), is(true));
        Manager manager = managerOptional.get();
        assertThat(manager.getUsername(), is("admin"));
        assertThat(manager.getPassword(), is("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS"));
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
        List<Manager> list = repository.findAll();
        assertThat(list, hasSize(1));
    }

    @Test
    @DisplayName("새로운 관리자 데이터를 등록한다.")
    void E6RA6() {
        Manager manager = Manager.builder()
                .username("manager")
                .password("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS")
                .build();
        repository.save(manager);

        Optional<Manager> optional = repository.findById(5L);
        assertThat(optional.isPresent(), is(true));
        Manager manager1 = optional.get();
        assertThat(manager1.getUsername(), is("manager"));
        assertThat(manager1.getPassword(), is("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS"));
        assertThat(manager1.getLastLoginDateTime(), notNullValue());

    }

    @Test
    @DisplayName("관리자데이트를 갱신한다.")
    void O6G() {
        repository.findById(1L).ifPresent(it -> {
            it.setUsername("manager2");
            it.setPassword("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS");
        });

        Optional<Manager> managerOptional = repository.findById(1L);
        assertThat(managerOptional.isPresent(), is(true));
        Manager manager = managerOptional.get();
        assertThat(manager.getUsername(), is("manager2"));
        assertThat(manager.getPassword(), is("{bcrypt}$2a$10$jSf5NBDRkzz9/IKc2GIjiOTynz/.5cMEt1wiSK0wYpn24ntqlKUBS"));

    }

}

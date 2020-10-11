package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.ProgressStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.*;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("경기결과 테스트")
@EnableJpaAuditing
class GameResultRepositoryTest extends DataJpaTest {

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private KakaoRepository kakaoRepository;

    @AfterEach
    void afterEach() {
        gameResultRepository.deleteAll();
    }

    @Test
    void insert() {

    }

    @Test
    void insert_read() {

    }

    @Test
    void insert_delete() {

    }
}

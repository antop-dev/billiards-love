package org.antop.billiardslove.jpa.repository;

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
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("당구사랑대회");
        contest.setEndDate(LocalDate.now());
        contest.setRegistrationUser(manager1);
        contest.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest);

        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        Member member1 = new Member();
        member1.setLoginToken("LoginToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member1.setKakaoLogin(kakaoLogin1);
        memberRepository.save(member1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        Member member2 = new Member();
        member2.setLoginToken("LoginToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member2.setKakaoLogin(kakaoLogin2);
        memberRepository.save(member2);

        Player player1 = new Player();
        player1.setContest(contest);
        player1.setMember(member1);
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setContest(contest);
        player2.setMember(member2);
        playerRepository.save(player2);

        GameResult gameResult = new GameResult();
        gameResult.setPlayerId(player1);
        gameResult.setOpponentPlayerId(player2);
        gameResult.setFirstResult(GameResultStatus.WIN);
        gameResult.setSecondResult(GameResultStatus.LOSE);
        gameResult.setThirdResult(GameResultStatus.NONE);
        gameResult.setConfirmationDateTime(LocalDateTime.now());
        gameResultRepository.save(gameResult);

        assertThat(gameResult.getId(), notNullValue());
        assertThat(gameResult.getPlayerId(), notNullValue());
        assertThat(gameResult.getOpponentPlayerId(), notNullValue());
        assertThat(gameResult.getFirstResult(), notNullValue());
        assertThat(gameResult.getSecondResult(), notNullValue());
        assertThat(gameResult.getThirdResult(), notNullValue());
        assertThat(gameResult.getConfirmationDateTime(), notNullValue());
    }

    @Test
    void insert_read() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("당구사랑대회");
        contest.setEndDate(LocalDate.now());
        contest.setRegistrationUser(manager1);
        contest.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest);

        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        Member member1 = new Member();
        member1.setLoginToken("LoginToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member1.setKakaoLogin(kakaoLogin1);
        memberRepository.save(member1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        Member member2 = new Member();
        member2.setLoginToken("LoginToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member2.setKakaoLogin(kakaoLogin2);
        memberRepository.save(member2);

        Player player1 = new Player();
        player1.setContest(contest);
        player1.setMember(member1);
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setContest(contest);
        player2.setMember(member2);
        playerRepository.save(player2);

        GameResult gameResult1 = new GameResult();
        gameResult1.setPlayerId(player1);
        gameResult1.setOpponentPlayerId(player2);
        gameResult1.setFirstResult(GameResultStatus.WIN);
        gameResult1.setSecondResult(GameResultStatus.LOSE);
        gameResult1.setThirdResult(GameResultStatus.NONE);
        gameResult1.setConfirmationDateTime(LocalDateTime.now());
        gameResultRepository.save(gameResult1);

        GameResult gameResult2 = new GameResult();
        gameResult2.setPlayerId(player1);
        gameResult2.setOpponentPlayerId(player2);
        gameResult2.setFirstResult(GameResultStatus.LOSE);
        gameResult2.setSecondResult(GameResultStatus.WIN);
        gameResult2.setThirdResult(GameResultStatus.NONE);
        gameResult2.setConfirmationDateTime(LocalDateTime.now());
        gameResultRepository.save(gameResult2);

        List<GameResult> gameResultList = gameResultRepository.findAll();

        assertThat(gameResultList, hasSize(2));
        assertThat(gameResultList, contains(gameResult1, gameResult2));
    }

    @Test
    void insert_delete() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("당구사랑대회");
        contest.setEndDate(LocalDate.now());
        contest.setRegistrationUser(manager1);
        contest.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest);

        KakaoLogin kakaoLogin1 = new KakaoLogin();
        kakaoLogin1.setAccessToken("accessToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin1);

        Member member1 = new Member();
        member1.setLoginToken("LoginToken1&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member1.setKakaoLogin(kakaoLogin1);
        memberRepository.save(member1);

        KakaoLogin kakaoLogin2 = new KakaoLogin();
        kakaoLogin2.setAccessToken("accessToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin2);

        Member member2 = new Member();
        member2.setLoginToken("LoginToken2&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member2.setKakaoLogin(kakaoLogin2);
        memberRepository.save(member2);

        Player player1 = new Player();
        player1.setContest(contest);
        player1.setMember(member1);
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setContest(contest);
        player2.setMember(member2);
        playerRepository.save(player2);

        GameResult gameResult1 = new GameResult();
        gameResult1.setPlayerId(player1);
        gameResult1.setOpponentPlayerId(player2);
        gameResult1.setFirstResult(GameResultStatus.WIN);
        gameResult1.setSecondResult(GameResultStatus.LOSE);
        gameResult1.setThirdResult(GameResultStatus.NONE);
        gameResult1.setConfirmationDateTime(LocalDateTime.now());
        gameResultRepository.save(gameResult1);

        GameResult gameResult2 = new GameResult();
        gameResult2.setPlayerId(player1);
        gameResult2.setOpponentPlayerId(player2);
        gameResult2.setFirstResult(GameResultStatus.LOSE);
        gameResult2.setSecondResult(GameResultStatus.WIN);
        gameResult2.setThirdResult(GameResultStatus.NONE);
        gameResult2.setConfirmationDateTime(LocalDateTime.now());
        gameResultRepository.save(gameResult2);

        gameResultRepository.deleteAll();

        assertThat(gameResultRepository.findAll(), IsEmptyCollection.empty());
    }
}
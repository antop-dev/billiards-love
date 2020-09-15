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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("선수 테스트")
@EnableJpaAuditing
class PlayerRepositoryTest extends DataJpaTest {

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
        playerRepository.deleteAll();
    }

    @Test
    void insert() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("제 1회 당구사랑대회");
        contest.setEndDate(LocalDate.now());
        contest.setRegistrationUser(manager1);
        contest.setProgressStatus(ProgressStatus.NONE);
        contestRepository.save(contest);

        KakaoLogin kakaoLogin = new KakaoLogin();
        kakaoLogin.setAccessToken("accessToken&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        kakaoRepository.save(kakaoLogin);

        Member member = new Member();
        member.setLoginToken("LoginToken&eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        member.setKakaoLogin(kakaoLogin);
        memberRepository.save(member);

        Player player = new Player();
        player.setContest(contest);
        player.setMember(member);
        playerRepository.save(player);

        assertThat(player.getId(), notNullValue());
        assertThat(player.getContest(), notNullValue());
        assertThat(player.getMember(), notNullValue());
    }

    @Test
    void insert_read() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("제 1회 당구 사랑 대회");
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

        List<Player> playerList = playerRepository.findAll();

        assertThat(playerList, hasSize(2));
        assertThat(playerList, contains(player1, player2));

    }

    @Test
    void insert_delete() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin123");
        manager1.setPassword("pass1");
        managerRepository.save(manager1);

        Contest contest = new Contest();
        contest.setTitle("당구사랑대회!");
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

        playerRepository.deleteAll();

        assertThat(playerRepository.findAll(), IsEmptyCollection.empty());
    }
}

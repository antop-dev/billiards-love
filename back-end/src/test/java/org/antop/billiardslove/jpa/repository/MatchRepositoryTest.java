package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.Outcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MatchRepositoryTest extends SpringBootBase {
    @Autowired
    private MatchRepository repository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("시합 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        contestRepository.save(contestData);

        Kakao kakao1 = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member1 = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao1)
                .build();
        memberRepository.save(member1);

        Player playerData1 = Player.builder()
                .contest(contestData)
                .member(member1)
                .handicap(30)
                .build();
        playerRepository.save(playerData1);

        Kakao kakao2 = Kakao.builder()
                .id(9999998L)
                .profile(Kakao.Profile.builder()
                        .nickname("띠용")
                        .imgUrl("pop")
                        .thumbUrl("bob").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member2 = Member.builder()
                .nickname("띠드래곤")
                .kakao(kakao2)
                .build();
        memberRepository.save(member2);

        Player playerData2 = Player.builder()
                .contest(contestData)
                .member(member2)
                .handicap(50)
                .build();
        playerRepository.save(playerData2);

        Match matchData = Match.builder()
                .contest(contestData)
                .player1(playerData1)
                .player2(playerData2)
                .build();

        repository.save(matchData);
        flushAndClear();

        // 2. 실행
        Optional<Match> optional = repository.findById(matchData.getId());

        // 3. 검증
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(match -> {
            assertThat(match.getId(), is(matchData.getId()));
            assertThat(match.getContest(), is(contestData));
            assertThat(match.getMe(member1.getId()), is(playerData1));
            assertThat(match.getMe(member2.getId()), is(playerData2));
            assertThat(match.getOpponent(member1.getId()), is(playerData2));
            assertThat(match.getOpponent(member2.getId()), is(playerData1));
        });
    }

    @Test
    @DisplayName("시합정보를 변경한다?")
    void change() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        contestRepository.save(contestData);

        Kakao kakao1 = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member1 = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao1)
                .build();
        memberRepository.save(member1);

        Player playerData1 = Player.builder()
                .contest(contestData)
                .member(member1)
                .handicap(30)
                .build();
        playerRepository.save(playerData1);

        Kakao kakao2 = Kakao.builder()
                .id(9999998L)
                .profile(Kakao.Profile.builder()
                        .nickname("띠용")
                        .imgUrl("pop")
                        .thumbUrl("bob").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member2 = Member.builder()
                .nickname("띠드래곤")
                .kakao(kakao2)
                .build();
        memberRepository.save(member2);

        Player playerData2 = Player.builder()
                .contest(contestData)
                .member(member2)
                .handicap(50)
                .build();
        playerRepository.save(playerData2);

        Match matchData = Match.builder()
                .contest(contestData)
                .player1(playerData1)
                .player2(playerData2)
                .build();

        repository.save(matchData);
        flushAndClear();

        // 2. 실행
        matchData.enterResult(member1.getId(), Outcome.WIN, Outcome.LOSE, Outcome.NONE);
        matchData.enterResult(member2.getId(), Outcome.LOSE, Outcome.LOSE, Outcome.NONE);
        Optional<Member> admin = memberRepository.findById(1L);
        repository.findById(matchData.getId()).ifPresent(match -> {
            match.decide(admin.get());
        });

        // 3. 검증
        Optional<Match> optional = repository.findById(matchData.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(match -> {
            assertThat(match.getId(), is(matchData.getId()));
            assertThat(match.getContest(), is(contestData));
            assertThat(match.getMe(member1.getId()), is(playerData1));
            assertThat(match.getMe(member2.getId()), is(playerData2));
            assertThat(match.getOpponent(member1.getId()), is(playerData2));
            assertThat(match.getOpponent(member2.getId()), is(playerData1));
            assertThat(match.isConfirmed(), is(true));
        });
    }

    @Test
    @DisplayName("삭제된 시합을 조회한다.")
    void delete() {
        // 1. 데이터 준비
        Contest contestData = Contest.builder()
                .title("코로나 추석 리그전 2021")
                .description("상금 : 갤러시 폴드 3")
                .startDate(LocalDate.of(2021, 9, 18))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 9, 30))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(16)
                .build();
        contestRepository.save(contestData);

        Kakao kakao1 = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member1 = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao1)
                .build();
        memberRepository.save(member1);

        Player playerData1 = Player.builder()
                .contest(contestData)
                .member(member1)
                .handicap(30)
                .build();
        playerRepository.save(playerData1);

        Kakao kakao2 = Kakao.builder()
                .id(9999998L)
                .profile(Kakao.Profile.builder()
                        .nickname("띠용")
                        .imgUrl("pop")
                        .thumbUrl("bob").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member2 = Member.builder()
                .nickname("띠드래곤")
                .kakao(kakao2)
                .build();
        memberRepository.save(member2);

        Player playerData2 = Player.builder()
                .contest(contestData)
                .member(member2)
                .handicap(50)
                .build();
        playerRepository.save(playerData2);

        Match matchData = Match.builder()
                .contest(contestData)
                .player1(playerData1)
                .player2(playerData2)
                .build();

        repository.save(matchData);
        flushAndClear();

        // 2. 실행
        repository.deleteById(matchData.getId());
        flushAndClear();

        // 3. 검증
        Assertions.assertThrows(MatchNotFoundException.class, () -> {
            repository.findById(matchData.getId()).orElseThrow(MatchNotFoundException::new);
        });
    }
}

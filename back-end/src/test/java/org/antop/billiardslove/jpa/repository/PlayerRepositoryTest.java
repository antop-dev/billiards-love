package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.exception.PlayerNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
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
import static org.hamcrest.Matchers.nullValue;

public class PlayerRepositoryTest extends SpringBootBase {
    @Autowired
    private PlayerRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("플레이어 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao)
                .build();
        memberRepository.save(member);

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

        Player playerData = Player.builder()
                .contest(contestData)
                .member(member)
                .handicap(30)
                .build();

        repository.save(playerData);
        flushAndClear();

        // 2. 실행
        Optional<Player> optional = repository.findById(playerData.getId());

        // 3. 검증
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(player -> {
            assertThat(player.getContest(), is(contestData));
            assertThat(player.getMember(), is(member));
            assertThat(player.getHandicap(), is(30));
        });
    }

    @Test
    @DisplayName("플레이어 정보를 변경한다.")
    void change() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao)
                .build();
        memberRepository.save(member);

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

        Player playerData = Player.builder()
                .contest(contestData)
                .member(member)
                .handicap(30)
                .build();

        repository.save(playerData);
        flushAndClear();

        // 2. 실행
        repository.findById(playerData.getId()).ifPresent(player -> {
            player.setNumber(1);
            player.setHandicap(70);
        });

        // 3. 검증
        Optional<Player> optional = repository.findById(playerData.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(player -> {
            assertThat(player.getContest(), is(contestData));
            assertThat(player.getMember(), is(member));
            assertThat(player.getHandicap(), is(70));
            assertThat(player.getNumber(), is(1));
            assertThat(player.getRank(), is(nullValue()));
            assertThat(player.getScore(), is(nullValue()));
        });
    }

    @Test
    @DisplayName("삭제된 플레이어를 조회한다.")
    void delete() {
        // 1. 데이터 준비
        Kakao kakao = Kakao.builder()
                .id(9999999L)
                .profile(Kakao.Profile.builder()
                        .nickname("도금수푼?")
                        .imgUrl("foo")
                        .thumbUrl("bar").build())
                .connectedAt(LocalDateTime.now())
                .build();

        Member member = Member.builder()
                .nickname("골드스푼")
                .kakao(kakao)
                .build();
        memberRepository.save(member);

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

        Player playerData = Player.builder()
                .contest(contestData)
                .member(member)
                .handicap(30)
                .build();

        repository.save(playerData);
        flushAndClear();

        // 2. 실행
        repository.deleteById(playerData.getId());
        flushAndClear();

        // 3. 검증
        Assertions.assertThrows(PlayerNotFoundException.class, () -> {
            repository.findById(playerData.getId()).orElseThrow(PlayerNotFoundException::new);
        });
    }
}

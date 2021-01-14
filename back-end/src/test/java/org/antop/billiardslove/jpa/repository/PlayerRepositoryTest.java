package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@DisplayName("선수 테스트")
class PlayerRepositoryTest extends DataJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("선수 데이터를 조회한다")
    void select() {
        Optional<Player> playerOptional = playerRepository.findById(1L);
        assertThat(playerOptional.isPresent(), is(true));
        Player player = playerOptional.get();
        assertThat(player.getNumber(), is(1));
        assertThat(player.getHandicap(), is(22));
        assertThat(player.getRank(), is(1));
        assertThat(player.getScore(), is(0));
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
        List<Player> list = playerRepository.findAll();
        assertThat(list, hasSize(6));
    }

    @Test
    @DisplayName("선수 데이터를 등록한다.")
    void insert() {
        Optional<Member> memberOptional = memberRepository.findById(1L);
        Optional<Contest> contestOptional = contestRepository.findById(1L);

        Player player = Player.builder()
                .contest(contestOptional.get())
                .member(memberOptional.get())
                .build();
        playerRepository.save(player);

        Optional<Player> playerOptional = playerRepository.findById(7L);
        assertThat(playerOptional.isPresent(), is(true));
        Player player1 = playerOptional.get();
        assertThat(player1.getNumber(), nullValue());
        assertThat(player1.getHandicap(), is(0));
        assertThat(player1.getRank(), nullValue());
        assertThat(player1.getScore(), is(0));
    }

    @Test
    @DisplayName("선수 데이터를 갱신한다.")
    void J6l1Z() {
        playerRepository.findById(1L).ifPresent(it -> {
            it.setNumber(99);
            it.setHandicap(30);
            it.setRank(1);
            it.setScore(3);
        });

        Optional<Player> playerOptional = playerRepository.findById(1L);
        assertThat(playerOptional.isPresent(), is(true));
        Player player = playerOptional.get();
        assertThat(player.getNumber(), is(99));
        assertThat(player.getHandicap(), is(30));
        assertThat(player.getRank(), is(1));
        assertThat(player.getScore(), is(3));
    }

}

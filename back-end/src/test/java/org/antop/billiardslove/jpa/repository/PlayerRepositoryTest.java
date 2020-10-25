package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("선수 테스트")
@EnableJpaAuditing
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
        assertThat(player.getId(), is(1L));
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

        System.out.println(player.toString());

        Optional<Player> playerOptional = playerRepository.findById(3L);
        assertThat(playerOptional.isPresent(), is(true));
        Player player1 = playerOptional.get();
        assertThat(player1.getId(), is(3L));
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
    }

}

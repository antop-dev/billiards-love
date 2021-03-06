package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.exception.PlayerNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

class PlayerRepositoryTest extends SpringBootBase {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void findById() {
        try {
            Player player1 = playerRepository.findById(1L).orElseThrow(PlayerNotFoundException::new);
            assertThat(player1.getHandicap(), is(22));
            assertThat(player1.getRank(), is(1));
            assertThat(player1.getScore(), is(150));

            Player player2 = playerRepository.findById(2L).orElseThrow(PlayerNotFoundException::new);
            assertThat(player2.getHandicap(), is(24));
            assertThat(player2.getRank(), is(2));
            assertThat(player2.getScore(), is(40));

            assertSame(player1.getContest(), player2.getContest());
        } catch (PlayerNotFoundException e) {
            fail("member is null");
        }
    }

    @Test
    void save() {
        Contest contest = contestRepository.findById(2L).orElseThrow(ContestNotFoundException::new);
        Member member = memberRepository.findById(1L).orElseThrow(MemberNotFountException::new);

        Player player = Player.builder()
                .contest(contest)
                .member(member)
                .handicap(27)
                .build();

        playerRepository.save(player);

        assertThat(player.getId(), notNullValue());
        assertThat(player.getId(), greaterThan(0L));
        assertThat(player.getNumber(), nullValue());
        assertThat(player.getRank(), nullValue());
        assertThat(player.getScore(), nullValue());
    }

    @Test
    void findByContestAndMember() {
        Contest contest = contestRepository.findById(1L).orElseThrow(ContestNotFoundException::new);
        Member member = memberRepository.findById(1L).orElseThrow(MemberNotFountException::new);

        Optional<Player> optional = playerRepository.findByContestAndMember(contest, member);
        assertThat(optional, isPresent());
        optional.ifPresent(player -> {
            assertThat(player.getHandicap(), is(22));
            assertThat(player.getRank(), is(1));
            assertThat(player.getScore(), is(150));
        });
    }

}

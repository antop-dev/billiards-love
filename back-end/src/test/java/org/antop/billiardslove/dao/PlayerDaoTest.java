package org.antop.billiardslove.dao;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PlayerDaoTest extends SpringBootBase {
    @Autowired
    private PlayerDao playerDao;

    @Test
    void findById() {
        Optional<Player> optional1 = playerDao.findById(1L);
        assertThat(optional1, isPresent());
        optional1.ifPresent(player -> {
            assertThat(player.getHandicap(), is(22));
            assertThat(player.getRank(), is(1));
            assertThat(player.getScore(), is(150));
        });

        Optional<Player> optional2 = playerDao.findById(2L);
        assertThat(optional2, isPresent());
        optional2.ifPresent(player -> {
            assertThat(player.getHandicap(), is(24));
            assertThat(player.getRank(), is(2));
            assertThat(player.getScore(), is(40));
        });
    }

    @Test
    void findByContestAndMember() {
        Optional<Player> optional = playerDao.findByContestAndMember(1, 1);
        assertThat(optional, isPresent());
        optional.ifPresent(player -> {
            assertThat(player.getHandicap(), is(22));
            assertThat(player.getRank(), is(1));
            assertThat(player.getScore(), is(150));
        });
    }

}

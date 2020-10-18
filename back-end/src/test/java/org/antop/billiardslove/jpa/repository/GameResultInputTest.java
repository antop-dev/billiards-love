package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.GameResultInput;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DisplayName("경기결과입력 테스트")
class GameResultInputTest extends DataJpaTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameResultInputRepository gameResultInputRepository;

    @Test
    @DisplayName("경기결과입력 데이터를 조회한다.")
    void AoEKf() {
        Optional<GameResultInput> optional = gameResultInputRepository.findById(2L);
        assertThat(optional.isPresent(), is(true));
        GameResultInput gameResultInput = optional.get();
        assertThat(gameResultInput.getId(), is(2L));
        List<GameResultInput> list = gameResultInputRepository.findAll();
        assertThat(list, hasSize(12));
    }

    @Test
    @DisplayName("경기결과입력 데이터를 등록한다.")
    void OeDOf() {
        Optional<Player> player1 = playerRepository.findById(1L);
        Optional<Player> player2 = playerRepository.findById(2L);

        GameResultInput gameResultInput = GameResultInput.builder()
                .player(player1.get())
                .opponentPlayer(player2.get())
                .firstResult(GameResultStatus.WIN)
                .secondResult(GameResultStatus.LOSE)
                .thirdResult(GameResultStatus.NONE)
                .inputDateTime(LocalDateTime.now())
                .build();
        gameResultInputRepository.save(gameResultInput);
        System.out.println(gameResultInput);

        Optional<GameResultInput> optional1 = gameResultInputRepository.findById(13L);
        assertThat(optional1.isPresent(), is(true));
        GameResultInput gameResultInput1 = optional1.get();
        assertThat(gameResultInput1.getId(), is(13L));
        List<GameResultInput> list = gameResultInputRepository.findAll();
        assertThat(list, hasSize(13));
    }

    @Test
    @DisplayName("경기결과입력 데이터를 갱신한다.")
    void J6l1Z() {
        gameResultInputRepository.findById(12L).ifPresent(it -> {
            it.setThirdResult(GameResultStatus.NONE);
            it.setInputDateTime(LocalDateTime.now());
        });

        Optional<GameResultInput> optional = gameResultInputRepository.findById(12L);
        assertThat(optional.isPresent(), is(true));
        GameResultInput gameResultInput = optional.get();
        assertThat(gameResultInput.getThirdResult(), is(GameResultStatus.NONE));
    }

    @Test
    @DisplayName("이미 존재하는 데이터를 등록하려고 한다.")
    void O6G() {
        Optional<Player> player1 = playerRepository.findById(1L);
        Optional<Player> player2 = playerRepository.findById(2L);

        GameResultInput gameResultInput = GameResultInput.builder()
                .id(12L)
                .player(player1.get())
                .opponentPlayer(player2.get())
                .firstResult(GameResultStatus.WIN)
                .secondResult(GameResultStatus.LOSE)
                .thirdResult(GameResultStatus.NONE)
                .inputDateTime(LocalDateTime.now())
                .build();
        gameResultInputRepository.save(gameResultInput);
        System.out.println(gameResultInput);

        Optional<GameResultInput> optional1 = gameResultInputRepository.findById(12L);
        assertThat(optional1.isPresent(), is(true));
        GameResultInput gameResultInput1 = optional1.get();
        assertThat(gameResultInput1.getId(), is(12L));
        assertThat(gameResultInput1.getFirstResult(), is(GameResultStatus.WIN));
        assertThat(gameResultInput1.getSecondResult(), is(GameResultStatus.LOSE));
        assertThat(gameResultInput1.getThirdResult(), is(GameResultStatus.NONE));

    }

}
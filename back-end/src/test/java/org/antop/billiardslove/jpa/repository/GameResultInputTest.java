package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.GameResultInput;
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

@DisplayName("경기결과입력 테스트")
@EnableJpaAuditing
class GameResultInputTest extends DataJpaTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameResultInputRepository gameResultInputRepository;

    @Test
    @DisplayName("경기결과입력 데이터를 조회한다.")
    void AoEKf() {
        Optional<GameResultInput> gameResultInputOptional = gameResultInputRepository.findById(2L);
        assertThat(gameResultInputOptional.isPresent(), is(true));
        GameResultInput gameResultInput = gameResultInputOptional.get();
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
                .build();
        gameResultInputRepository.save(gameResultInput);

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
            it.setThirdResult(GameResultStatus.ABSTENTION);
        });

        Optional<GameResultInput> gameResultInputOptional = gameResultInputRepository.findById(12L);
        assertThat(gameResultInputOptional.isPresent(), is(true));
        GameResultInput gameResultInput = gameResultInputOptional.get();
        assertThat(gameResultInput.getThirdResult(), is(GameResultStatus.ABSTENTION));
    }


}
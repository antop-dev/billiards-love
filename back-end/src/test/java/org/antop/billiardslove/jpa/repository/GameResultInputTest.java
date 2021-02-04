package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.GameResultInput;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("경기결과입력 테스트")
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
        assertThat(gameResultInput.getFirstResult(), is(GameResultStatus.WIN));
        assertThat(gameResultInput.getSecondResult(), is(GameResultStatus.WIN));
        assertThat(gameResultInput.getThirdResult(), is(GameResultStatus.WIN));
        assertThat(gameResultInput.getInputDateTime(), notNullValue());
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
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
                .build();
        gameResultInputRepository.save(gameResultInput);

        Optional<GameResultInput> optional1 = gameResultInputRepository.findById(13L);
        assertThat(optional1.isPresent(), is(true));
        GameResultInput gameResultInput1 = optional1.get();
        assertThat(gameResultInput1.getPlayer(), is(player1.get()));
        assertThat(gameResultInput1.getOpponentPlayer(), is(player2.get()));
        assertThat(gameResultInput1.getFirstResult(), is(GameResultStatus.NONE));
        assertThat(gameResultInput1.getSecondResult(), is(GameResultStatus.NONE));
        assertThat(gameResultInput1.getThirdResult(), is(GameResultStatus.NONE));
        assertThat(gameResultInput1.getInputDateTime(), notNullValue());
    }

    @Test
    @DisplayName("경기결과입력 데이터를 갱신한다.")
    void J6l1Z() {
        gameResultInputRepository.findById(12L).ifPresent(it -> it.setThirdResult(GameResultStatus.ABSTENTION));

        Optional<GameResultInput> gameResultInputOptional = gameResultInputRepository.findById(12L);
        assertThat(gameResultInputOptional.isPresent(), is(true));
        GameResultInput gameResultInput = gameResultInputOptional.get();
        assertThat(gameResultInput.getThirdResult(), is(GameResultStatus.ABSTENTION));
        assertThat(gameResultInput.getInputDateTime(), notNullValue());
    }


}

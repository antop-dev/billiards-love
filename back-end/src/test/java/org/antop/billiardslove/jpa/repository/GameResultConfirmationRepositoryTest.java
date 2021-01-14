package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.DataJpaTest;
import org.antop.billiardslove.jpa.entity.GameResultConfirmation;
import org.antop.billiardslove.jpa.entity.GameResultInput;
import org.antop.billiardslove.jpa.entity.Manager;
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
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("경기결과확정 테이블 테스트")
class GameResultConfirmationRepositoryTest extends DataJpaTest {

    @Autowired
    private GameResultConfirmationRepository gameResultConfirmationRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private GameResultInputRepository gameResultInputRepository;

    @Test
    @DisplayName("경기결과확정 데이터를 조회한다.")
    void AoEKf() {
        Optional<GameResultConfirmation> gameResultConfirmationOptional = gameResultConfirmationRepository.findById(2L);
        assertThat(gameResultConfirmationOptional.isPresent(), is(true));
        GameResultConfirmation gameResultConfirmation = gameResultConfirmationOptional.get();
        assertThat(gameResultConfirmation.getFirstResult(), is(GameResultStatus.WIN));
        assertThat(gameResultConfirmation.getSecondResult(), is(GameResultStatus.WIN));
        assertThat(gameResultConfirmation.getThirdResult(), is(GameResultStatus.WIN));
        assertThat(gameResultConfirmation.getConfirmationDateTime(), notNullValue());
    }

    @Test
    @DisplayName("데이터베이스 스텁을 통해 SQL문이 정상적으로 잘 들어갔는지 사이즈 체크")
    void AE07R4() {
        List<GameResultConfirmation> list = gameResultConfirmationRepository.findAll();
        assertThat(list, hasSize(6));
    }

    @Test
    @DisplayName("경기결과확정 데이터를 등록한다.")
    void OeDOf() {
        Optional<Player> player1 = playerRepository.findById(1L);
        Optional<GameResultInput> gameResultInput1 = gameResultInputRepository.findById(1L);

        Optional<Player> player2 = playerRepository.findById(2L);
        Optional<GameResultInput> gameResultInput2 = gameResultInputRepository.findById(2L);

        Optional<Manager> manager = managerRepository.findById(1L);

        GameResultConfirmation gameResultConfirmation = GameResultConfirmation.builder()
                .player(player1.get())
                .playerGameResultInput(gameResultInput1.get())
                .opponentPlayer(player2.get())
                .opponentGameResultInput(gameResultInput2.get())
                .manager(manager.get())
                .build();
        gameResultConfirmationRepository.save(gameResultConfirmation);

        Optional<GameResultConfirmation> optional1 = gameResultConfirmationRepository.findById(7L);
        assertThat(optional1.isPresent(), is(true));
        GameResultConfirmation gameResultConfirmation1 = optional1.get();
        assertThat(gameResultConfirmation1.getPlayer(), is((player1.get())));
        assertThat(gameResultConfirmation1.getPlayerGameResultInput(), is((gameResultInput1.get())));
        assertThat(gameResultConfirmation1.getOpponentPlayer(), is((player2.get())));
        assertThat(gameResultConfirmation1.getOpponentGameResultInput(), is((gameResultInput2.get())));
        assertThat(gameResultConfirmation1.getPlayer(), is((player1.get())));
        assertThat(gameResultConfirmation1.getFirstResult(), is(GameResultStatus.NONE));
        assertThat(gameResultConfirmation1.getSecondResult(), is(GameResultStatus.NONE));
        assertThat(gameResultConfirmation1.getThirdResult(), is(GameResultStatus.NONE));
        assertThat(gameResultConfirmation1.getConfirmationDateTime(), notNullValue());
    }

    @Test
    @DisplayName("경기결과확정 데이터를 갱신한다.")
    void J6l1Z() {
        gameResultConfirmationRepository.findById(6L).ifPresent(it -> {
            it.setSecondResult(GameResultStatus.ABSTENTION);
            it.setThirdResult(GameResultStatus.ABSTENTION);
        });

        Optional<GameResultConfirmation> gameResultConfirmationOptional = gameResultConfirmationRepository.findById(6L);
        assertThat(gameResultConfirmationOptional.isPresent(), is(true));
        GameResultConfirmation gameResultConfirmation = gameResultConfirmationOptional.get();
        assertThat(gameResultConfirmation.getSecondResult(), is(GameResultStatus.ABSTENTION));
        assertThat(gameResultConfirmation.getThirdResult(), is(GameResultStatus.ABSTENTION));
    }

}

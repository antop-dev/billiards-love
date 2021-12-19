package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class PlayerMapperTest {
    @Autowired
    private PlayerMapper mapper;

    @Test
    void toDto() {
        Player player = Player.builder()
                .contest(null)
                .handicap(21)
                .member(Member.builder().nickname("안탑").build())
                .build();
        player.setRank(1);
        player.setNumber(112);
        player.incrementScore(1192);

        PlayerDto dto = mapper.toDto(player);

        assertThat(dto, notNullValue());
        assertThat(dto.getHandicap(), is(player.getHandicap()));
        assertThat(dto.getNickname(), is(player.getMember().getNickname()));
        assertThat(dto.getRank(), is(player.getRank()));
        assertThat(dto.getNumber(), is(player.getNumber()));
        assertThat(dto.getScore(), is(player.getScore()));
    }
}

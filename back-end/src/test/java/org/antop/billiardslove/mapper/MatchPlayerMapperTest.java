package org.antop.billiardslove.mapper;

import org.antop.billiardslove.dto.MatchPlayerDto;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.MatchResult;
import org.antop.billiardslove.model.Outcome;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class MatchPlayerMapperTest {
    private final MatchPlayerMapper mapper = new MatchPlayerMapperImpl();

    @Test
    void toDto() {
        // given
        Member member = Member.builder().nickname("안탑").build();
        Player player = Mockito.mock(Player.class);
        when(player.getId()).thenReturn(1921L);
        when(player.getNumber()).thenReturn(10);
        when(player.getHandicap()).thenReturn(22);
        when(player.getMember()).thenReturn(member);
        when(player.getRank()).thenReturn(4);
        when(player.getScore()).thenReturn(312);
        when(player.getVariation()).thenReturn(0);
        MatchResult result = MatchResult.of(Outcome.LOSE, Outcome.ABSTENTION, Outcome.HOLD);
        // when
        MatchPlayerDto dto = mapper.toDto(player, result);
        // then
        assertThat(dto, notNullValue());
        assertThat(dto.getId(), is(player.getId()));
        assertThat(dto.getNumber(), is(player.getNumber()));
        assertThat(dto.getHandicap(), is(player.getHandicap()));
        assertThat(dto.getNickname(), is(member.getNickname()));
        assertThat(dto.getRank(), is(player.getRank()));
        assertThat(dto.getScore(), is(player.getScore()));
        assertThat(dto.getVariation(), is(player.getVariation()));
        assertThat(dto.getResult(), is(new String[]{"LOSE", "ABSTENTION", "HOLD"}));
    }
}

package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.Outcome;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class MatchMapperTest {
    @Autowired
    private MatchMapper mapper;

    @Test
    void toDto() {
        // player1
        Member member1 = mock(Member.class);
        when(member1.getId()).thenReturn(1L);
        Player player1 = Player.builder().member(member1).build();
        // player2 (opponent)
        Member member2 = mock(Member.class);
        when(member2.getId()).thenReturn(2L);
        when(member2.getNickname()).thenReturn("다니엘 산체스");
        Player player2 = Player.builder()
                .member(member2)
                .handicap(40)
                .build();
        player2.setNumber(8);
        player2.setRank(10);
        player2.incrementScore(871);

        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .build();
        match.enterResult(member1.getId(), Outcome.WIN, Outcome.LOSE, Outcome.WIN);
        match.enterResult(member2.getId(), Outcome.LOSE, Outcome.WIN, Outcome.LOSE);

        MatchDto dto = mapper.toDto(match, member1);

        assertThat(dto, notNullValue());
        assertThat(dto.getLeft().getResult(), is(new String[]{"WIN", "LOSE", "WIN"}));
        assertThat(dto.getRight().getNickname(), is(member2.getNickname()));
        assertThat(dto.getRight().getNumber(), is(player2.getNumber()));
        assertThat(dto.getRight().getRank(), is(player2.getRank()));
        assertThat(dto.getRight().getScore(), is(player2.getScore()));
    }
}

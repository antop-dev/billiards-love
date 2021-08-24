package org.antop.billiardslove.mapper;

import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class ContestMapperTest {
    @Autowired
    private ContestMapper mapper;
    @MockBean
    private PlayerService playerService;

    @Test
    void toDto() {
        // stub
        PlayerDto player = PlayerDto.builder()
                .nickname("안탑")
                .handicap(21)
                .build();
        // mock
        when(playerService.getPlayer(anyLong(), anyLong())).thenReturn(Optional.of(player));
        // entity (spy)
        Contest contest = spy(Contest.builder()
                .title("2021 당구왕 선발 리그전")
                .description("총 상금 2000만원!")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(128)
                .build());
        when(contest.getId()).thenReturn(1L);
        // convert
        ContestDto dto = mapper.toDto(contest, 1L);
        // verify
        assertThat(dto, notNullValue());
        assertThat(dto.getTitle(), is(contest.getTitle()));
        assertThat(dto.getDescription(), is(contest.getDescription()));
        assertThat(dto.getStartDate(), is(contest.getStartDate()));
        assertThat(dto.getStartTime(), is(contest.getStartTime()));
        assertThat(dto.getEndDate(), is(contest.getEndDate()));
        assertThat(dto.getEndTime(), is(contest.getEndTime()));
        assertThat(dto.getMaxJoiner(), is(contest.getMaxJoiner()));
        assertThat(dto.getPlayer().getNickname(), is(player.getNickname()));
        assertThat(dto.getPlayer().getHandicap(), is(player.getHandicap()));
    }

    @Test
    void playerIsNull() {
        // entity (spy)
        Contest contest = spy(Contest.builder()
                .title("2021 당구왕 선발 리그전")
                .description("총 상금 2000만원!")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(0, 0, 0))
                .endDate(LocalDate.of(2021, 12, 31))
                .endTime(LocalTime.of(23, 59, 59))
                .maxJoiner(128)
                .build());
        when(contest.getId()).thenReturn(2L);
        // convert
        ContestDto dto = mapper.toDto(contest);
        // verify
        assertThat(dto, notNullValue());
        assertThat(dto.getPlayer(), nullValue());
    }
}

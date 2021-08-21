package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestRankApi {
    private final PlayerService playerService;

    @GetMapping("/api/v1/contest/{id}/ranks")
    public List<PlayerDto> ranks(@PathVariable(name = "id") long contestId) {
        Comparator<PlayerDto> compare = Comparator
                .comparing((PlayerDto p) -> ofNullable(p.getRank()).orElse(Integer.MAX_VALUE))
                .thenComparing((PlayerDto p) -> ofNullable(p.getNumber()).orElse(Integer.MAX_VALUE));

        return playerService.getPlayers(contestId).stream()
                .sorted(compare)
                .collect(Collectors.toList());
    }

}

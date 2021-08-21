package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.service.MatchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 대진표 조회 API
 *
 * @author antop
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class MatchApi {
    private final MatchService matchService;

    @GetMapping("/api/v1/contest/{contestId}/matches")
    public List<MatchDto> matches(@PathVariable("contestId") final long contestId,
                                  @AuthenticationPrincipal final Long memberId) {
        return matchService.getMatches(contestId, memberId);
    }

    @GetMapping("/api/v1/match/{matchId}")
    public MatchDto match(@PathVariable("matchId") final long matchId,
                          @AuthenticationPrincipal final Long memberId) {
        return matchService.getMatch(matchId, memberId).orElseThrow(MatchNotFoundException::new);
    }

    @PutMapping("/api/v1/match/{matchId}")
    public void result(@PathVariable("matchId") final long matchId,
                       @RequestBody String[] result,
                       @AuthenticationPrincipal final Long memberId) {
        Match.Result[] results = Arrays.stream(result).map(Match.Result::valueOf).toArray(value -> new Match.Result[3]);
        matchService.enter(matchId, memberId, results);
    }

}

package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.service.MatchGetService;
import org.antop.billiardslove.service.MatchResultService;
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
    private final MatchGetService matchGetService;
    private final MatchResultService matchResultService;

    @GetMapping("/api/v1/contest/{contestId}/matches")
    public List<MatchDto> matches(@PathVariable("contestId") final long contestId,
                                  @AuthenticationPrincipal final Long memberId) {
        return matchGetService.getMatches(contestId, memberId);
    }

    @GetMapping("/api/v1/match/{matchId}")
    public MatchDto match(@PathVariable("matchId") final long matchId,
                          @AuthenticationPrincipal final Long memberId) {
        return matchGetService.getMatch(matchId, memberId);
    }

    @PutMapping("/api/v1/match/{matchId}")
    public void result(@PathVariable("matchId") final long matchId,
                       @RequestBody String[] result,
                       @AuthenticationPrincipal final Long memberId) {
        log.debug("result = {}", Arrays.toString(result));
        Match.Result[] results = Arrays.stream(result).map(Match.Result::valueOf).toArray(value -> new Match.Result[3]);
        matchResultService.enter(matchId, memberId, results);
    }

}

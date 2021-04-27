package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.service.MatchGetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 대진표 조회 API
 *
 * @author antop
 */
@RequiredArgsConstructor
@RestController
public class MatchApi {
    private final MatchGetService matchGetService;

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

}

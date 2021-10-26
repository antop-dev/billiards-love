package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.BadRequestException;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public MatchDto enter(@PathVariable("matchId") final long matchId,
                          @RequestBody MatchEnterRequest request,
                          @AuthenticationPrincipal final Long memberId) {
        if (request.getResult().length != 3) throw new BadRequestException();
        // String[] -> Outcome[]
        Outcome[] results = new Outcome[3];
        try {
            for (int i = 0; i < request.getResult().length; i++) {
                results[i] = Outcome.valueOf(request.getResult()[i]);
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException();
        }

        return matchService.enter(matchId, memberId, results);
    }

    @Getter
    @ToString
    @FieldNameConstants
    static class MatchEnterRequest {
        /**
         * 선수가 입력한 경기 결과
         */
        private final String[] result;

        @JsonCreator
        public MatchEnterRequest(@JsonProperty String[] result) {
            this.result = result;
        }
    }

}

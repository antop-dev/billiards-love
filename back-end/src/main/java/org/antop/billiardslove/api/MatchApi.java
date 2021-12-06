package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.ManagerAuthorize;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.exception.MatchNotFoundException;
import org.antop.billiardslove.model.Outcome;
import org.antop.billiardslove.service.MatchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
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
    public MatchDto enterResult(@PathVariable("matchId") final long matchId,
                                @RequestBody @Valid MatchResultEnterRequest request,
                                @AuthenticationPrincipal final Long memberId) {
        return matchService.enter(matchId, memberId, request.getResult());
    }

    @ManagerAuthorize
    @PostMapping("/api/v1/match/{matchId}/decide")
    public MatchDto decide(@PathVariable("matchId") final long matchId,
                           @RequestBody @Valid MatchDecideRequest request,
                           @AuthenticationPrincipal final Long memberId) {
        return matchService.decide(matchId, memberId, request.getLeft(), request.getRight());
    }

    @Getter
    @ToString
    @FieldNameConstants
    static class MatchResultEnterRequest {
        /**
         * 선수가 입력한 경기 결과
         */
        @Size(min = 3, max = 3, message = "경기 결과의 크기는 3개입니다.")
        private final Outcome[] result;

        @JsonCreator
        public MatchResultEnterRequest(@JsonProperty Outcome[] result) {
            this.result = result;
        }
    }

    @Getter
    @ToString
    @FieldNameConstants
    public static class MatchDecideRequest {
        /**
         * 왼쪽 선수 경기 결과
         */
        @Size(min = 3, max = 3, message = "경기 결과의 크기는 3개입니다.")
        private final Outcome[] left;
        /**
         * 오른쪽 선수 경기 결과
         */
        @Size(min = 3, max = 3, message = "경기 결과의 크기는 3개입니다.")
        private final Outcome[] right;

        @Builder
        @JsonCreator
        public MatchDecideRequest(@JsonProperty Outcome[] left,
                                  @JsonProperty Outcome[] right) {
            this.left = left;
            this.right = right;
        }
    }

}

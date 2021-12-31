package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 대회 참가 API
 *
 * @author jammini
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestJoiningApi {
    private final ContestService contestService;

    @PostMapping("/api/v1/contest/{id}/join")
    public ContestDto join(@PathVariable(name = "id") long contestId,
                           @AuthenticationPrincipal Long memberId,
                           @RequestBody @Valid JoiningRequest request) {
        return contestService.join(contestId, memberId, request.getHandicap());
    }

    @DeleteMapping("/api/v1/contest/{id}/join")
    public ContestDto cancelJoining(@PathVariable(name = "id") long contestId,
                                    @AuthenticationPrincipal Long memberId) {
        return contestService.cancelJoin(contestId, memberId);
    }

    /**
     * 대회 참가 신청 요청
     */
    @Getter
    @FieldNameConstants
    public static class JoiningRequest {
        /**
         * 참가 핸디
         */
        @NotNull(message = "참가 핸디를 입력하세요.")
        private final Integer handicap;

        @JsonCreator
        public JoiningRequest(@JsonProperty Integer handicap) {
            this.handicap = handicap;
        }
    }
}

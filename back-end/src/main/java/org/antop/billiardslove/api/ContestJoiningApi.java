package org.antop.billiardslove.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void join(@PathVariable(name = "id") long contestId,
                     @AuthenticationPrincipal Long memberId,
                     @RequestBody JoiningRequest request) {
        contestService.join(contestId, memberId, request.getHandicap());
    }

    @DeleteMapping("/api/v1/contest/{id}/join")
    public void cancelJoining(@PathVariable(name = "id") long contestId,
                              @AuthenticationPrincipal Long memberId) {
        contestService.cancelJoin(contestId, memberId);
    }

    /**
     * 대회 참가 신청 요청
     */
    @Getter
    static class JoiningRequest {
        /**
         * 참가 핸디캡
         */
        private int handicap;
    }
}

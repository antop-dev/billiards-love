package org.antop.billiardslove.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.CantParticipationException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class ContestParticipationApi {

    private final ContestService contestService;

    @PostMapping("/api/v1/contest/{id}/participate")
    public void participationCheck(@PathVariable(name = "id") long contestId, @AuthenticationPrincipal Long memberId,
                                   @RequestBody Request request) {
        try {
            contestService.participate(contestId, memberId, request.getHandicap());
        } catch (CantParticipationException | AlreadyParticipationException |
                ContestNotFoundException | MemberNotFountException e) {
            e.printStackTrace();
        }
    }
    @Getter
    static class Request {
        /**
         * 핸디캡
         */
        private int handicap;
    }
}

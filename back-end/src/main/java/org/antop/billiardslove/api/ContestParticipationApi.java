package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.error.ErrorMessage;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Object participationCheck(@PathVariable(name = "id") long contestId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        long memberId = Long.parseLong(auth.getName());

        String result = contestService.getParticipationCheck(contestId, memberId);

        if (result.equals("")) {
            return result;
        } else {
            return ErrorMessage.badRequest(result);
        }
    }
}

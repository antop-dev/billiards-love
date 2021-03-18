package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대회 상태 변경 API
 *
 * @author antop
 */
@RequiredArgsConstructor
@RestController
@Secured(JwtAuthenticationToken.ROLE_MANAGER)
public class ContestStateApi {
    private final ContestService contestService;

    @PostMapping("api/v1/contest/{id}/stop")
    public void stop(@PathVariable("id") long contestId) {
        contestService.stop(contestId);
    }

}

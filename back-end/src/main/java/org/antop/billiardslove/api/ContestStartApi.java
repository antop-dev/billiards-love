package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대회 시작 API
 *
 * @author antop
 */
@RequiredArgsConstructor
@RestController
public class ContestStartApi {
    private final ContestService contestService;

    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PostMapping("/api/v1/contest/{id}/start")
    public void start(@PathVariable("id") long contestId) {
        contestService.start(contestId);
    }

}

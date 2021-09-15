package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.dto.ContestDto;
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

    @PostMapping("/api/v1/contest/{id}/open")
    public ContestDto open(@PathVariable("id") long contestId) {
        return contestService.open(contestId);
    }

    @PostMapping("/api/v1/contest/{id}/start")
    public ContestDto start(@PathVariable("id") long contestId) {
        return contestService.start(contestId);
    }

    @PostMapping("api/v1/contest/{id}/stop")
    public ContestDto stop(@PathVariable("id") long contestId) {
        return contestService.stop(contestId);
    }

    @PostMapping("/api/v1/contest/{id}/end")
    public ContestDto end(@PathVariable("id") long contestId) {
        return contestService.end(contestId);
    }

}

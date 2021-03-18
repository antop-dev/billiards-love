package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ContestEndApi {
    private final ContestService contestService;

    @PostMapping("/api/v1/contest/{id}/end")
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    public void open(@PathVariable("id") long contestId) {
        contestService.end(contestId);
    }
}

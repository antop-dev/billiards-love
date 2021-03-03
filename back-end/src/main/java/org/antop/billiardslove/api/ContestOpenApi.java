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
public class ContestOpenApi {
    private final ContestService contestService;

    @PostMapping("/api/v1/contest/{id}/open")
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    public void open(@PathVariable("id") long contestId) {
        contestService.open(contestId);
        // TODO: MapStruct 적용 후 변경된 대회정보를 응답으로 주자
    }

}

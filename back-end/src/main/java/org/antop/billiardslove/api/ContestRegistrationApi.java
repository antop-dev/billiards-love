package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대회 등록 API
 *
 * @author jammini
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestRegistrationApi {
    private final ContestService contestService;

    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PostMapping("/api/v1/contest")
    public ContestResponse register(@RequestBody ContestRegistrationRequest request) {

        ContestDto contestDto = ContestDto.builder()
                .title(request.getName())
                .description(request.getDescription())
                .startDate(request.getStart().getStartDate())
                .startTime(request.getStart().getStartTime())
                .endDate(request.getEnd().getEndDate())
                .endTime(request.getEnd().getEndTime())
                .maxJoiner(request.getMaxJoiner())
                .build();

        Contest contest = contestService.register(contestDto);

        return ContestResponse.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .start(ContestResponse.Start.builder()
                        .startDate(contest.getStartDate())
                        .startTime(contest.getStartTime())
                        .build())
                .end(ContestResponse.End.builder()
                        .endDate(contest.getEndDate())
                        .endTime(contest.getEndTime())
                        .build())
                .state(ContestResponse.State.builder()
                        .code(contest.getState().getCode())
                        .name(contest.getState().name())
                        .build())
                .maxJoiner(contest.getMaxJoiner())
                .build();
    }
}

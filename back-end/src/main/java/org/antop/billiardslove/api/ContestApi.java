package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.ContestRank;
import org.antop.billiardslove.service.ContestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 대회 API
 *
 * @author jammini
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestApi {
    private final ContestService contestService;

    @GetMapping("/api/v1/contest/{id}")
    public ContestInfoResponse InformationApi(@PathVariable(name = "id") long id) {

        ContestDto contest = contestService.getContest(id);

        return contestInfoResponse(contest);
    }

    @GetMapping("/api/v1/contests")
    public List<ContestInfoResponse> listApi() {

        List<ContestDto> contestList = contestService.getAllContests();

        return contestList.stream().map(this::contestInfoResponse).collect(Collectors.toList());
    }

    @GetMapping("/api/v1/contest/{id}/rank")
    public List<ContestRankResponse> rankApi(@PathVariable(name = "id") long id) {

        List<ContestRank> contestRankList = contestService.getRanks(id);

        return contestRankList.stream().map(o -> ContestRankResponse.builder()
                .rank(o.getRank())
                .participant(ContestRankResponse.Participant.builder()
                        .id(o.getParticipant().getId())
                        .name(o.getParticipant().getName())
                        .handicap(o.getParticipant().getHandicap())
                        .build())
                .progress(0)
                .score(o.getScore()).build()).collect(Collectors.toList());
    }

    private ContestInfoResponse contestInfoResponse(ContestDto contest) {

        return ContestInfoResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .start(ContestInfoResponse.Start.builder()
                        .startDate(contest.getStartDate())
                        .startTime(contest.getStartTime())
                        .build())
                .end(ContestInfoResponse.End.builder()
                        .endDate(contest.getEndDate())
                        .endTime(contest.getEndTime())
                        .build())
                .state(ContestInfoResponse.State.builder()
                        .code(contest.getCode())
                        .name(contest.getState())
                        .build())
                .maximumParticipants(contest.getMaximumParticipants())
                .build();
    }
}

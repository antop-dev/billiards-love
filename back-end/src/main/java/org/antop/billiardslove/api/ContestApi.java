package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.service.ContestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        ContestDto contest = contestService.getContestInfo(id);

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

    @GetMapping("/api/v1/contests")
    public List<ContestInfoResponse> listApi() {

        List<ContestDto> contestList = contestService.getContestList();
        List<ContestInfoResponse> list = new ArrayList<>();

        for (int i = 0; i < contestList.size(); i++) {
            list.add(ContestInfoResponse.builder()
                    .id(contestList.get(i).getId())
                    .name(contestList.get(i).getName())
                    .description(contestList.get(i).getDescription())
                    .start(ContestInfoResponse.Start.builder()
                            .startDate(contestList.get(i).getStartDate())
                            .startTime(contestList.get(i).getStartTime())
                            .build())
                    .end(ContestInfoResponse.End.builder()
                            .endDate(contestList.get(i).getEndDate())
                            .endTime(contestList.get(i).getEndTime())
                            .build())
                    .state(ContestInfoResponse.State.builder()
                            .code(contestList.get(i).getCode())
                            .name(contestList.get(i).getState())
                            .build())
                    .maximumParticipants(contestList.get(i).getMaximumParticipants())
                    .build());
        }
        return list;
    }

    @GetMapping("/api/v1/contest/{id}/rank")
    public List<ContestRankResponse> rankApi(@PathVariable(name = "id") long id) {

        List<Player> contestList = contestService.getContestRank(id);
        List<ContestRankResponse> list = new ArrayList<>();

        for (int i = 0; i < contestList.size(); i++) {
            list.add(ContestRankResponse.builder()
                    .rank(contestList.get(i).getRank())
                    .participant(ContestRankResponse.Participant.builder()
                            .id(contestList.get(i).getMember().getId())
                            .name(contestList.get(i).getMember().getNickname())
                            .handicap(contestList.get(i).getMember().getHandicap())
                            .build())
                    .progress(0)
                    .score(contestList.get(i).getScore()).build());
        }
        return list;
    }
}

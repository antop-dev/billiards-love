package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.antop.billiardslove.jpa.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContestService {
    private final ContestRepository contestRepository;

    private final PlayerRepository playerRepository;

    /**
     * 대회 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 정보
     */
    public ContestDto getContestInfo(long id) {

        Contest contest = contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);

        return ContestDto.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .startDate(contest.getStartDate())
                .startTime(contest.getStartTime())
                .endDate(contest.getEndDate())
                .endTime(contest.getEndTime())
                .code(contest.getState().getCode())
                .state(contest.getState().name())
                .maximumParticipants(contest.getMaximumParticipants())
                .build();
    }

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    public List<ContestDto> getContestList() {

        List<Contest> contestList = contestRepository.findAllByOrderByStateAsc();
        List<ContestDto> list = new ArrayList<>();

        for (int i = 0; i < contestList.size(); i++) {
            list.add(ContestDto.builder()
                    .id(contestList.get(i).getId())
                    .name(contestList.get(i).getTitle())
                    .description(contestList.get(i).getDescription())
                    .startDate(contestList.get(i).getStartDate())
                    .startTime(contestList.get(i).getStartTime())
                    .endDate(contestList.get(i).getEndDate())
                    .endTime(contestList.get(i).getEndTime())
                    .code(contestList.get(i).getState().getCode())
                    .name(contestList.get(i).getState().name())
                    .maximumParticipants(contestList.get(i).getMaximumParticipants())
                    .build());
        }
        return list;
    }

    /**
     * 대회 순위 정보 조회
     *
     * @param id 대회 아이디
     * @return 대회 참여 플레이어
     */
    public List<Player> getContestRank(long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);
        return playerRepository.findByContestOrderByRankAsc(contest);
    }
}

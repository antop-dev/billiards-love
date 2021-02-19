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

import java.util.List;
import java.util.stream.Collectors;

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

        return contestDto(contest);
    }

    /**
     * 대회 목록 조회
     *
     * @return 대회 목록
     */
    public List<ContestDto> getContestList() {

        List<Contest> contestList = contestRepository.findAllByOrderByStateAsc();

        return contestList.stream().map(this::contestDto).collect(Collectors.toList());
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

    private ContestDto contestDto(Contest contest) {
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
}

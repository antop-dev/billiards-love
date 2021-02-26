package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.ContestRankDto;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.CantParticipationException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.ContestRepository;
import org.antop.billiardslove.jpa.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final PlayerRepository playerRepository;
    private final MemberService memberService;
    private final PlayerService playerService;

    @Override
    public ContestDto getContest(long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);
        return contestDto(contest);
    }

    @Override
    public List<ContestDto> getAllContests() {
        List<Contest> contestList = contestRepository.findAllByOrderByStateAsc();
        return contestList.stream().map(this::contestDto).collect(Collectors.toList());
    }

    @Override
    public List<ContestRankDto> getRanks(long id) {
        Contest contest = contestRepository.findById(id).orElseThrow(ContestNotFoundException::new);
        List<Player> list = playerRepository.findByContestOrderByRankAsc(contest);
        return list.stream().map(this::contestRankDto).collect(Collectors.toList());
    }

    @Override
    public void participate(long contestId, long memberId, int handicap) throws CantParticipationException, AlreadyParticipationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        if (!contest.isAccepting()) {
            throw new CantParticipationException();
        }
        Member member = memberService.getMember(memberId);
        Player player = playerService.getPlayer(memberId);

        if (player != null) {
            throw new AlreadyParticipationException();
        }
        playerRepository.save(Player.builder()
                .contest(contest)
                .member(member)
                .handicap(handicap)
                .build());
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

    private ContestRankDto contestRankDto(Player o) {
        return ContestRankDto.builder()
                .rank(o.getRank())
                .participant(ContestRankDto.Participant.builder()
                        .id(o.getMember().getId())
                        .name(o.getMember().getNickname())
                        .handicap(o.getMember().getHandicap())
                        .build())
                // TODO: 진행률 계산
                .progress(0)
                .score(o.getScore()).build();
    }
}

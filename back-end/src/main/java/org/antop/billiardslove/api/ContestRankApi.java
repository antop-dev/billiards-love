package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dto.ContestRankDto;
import org.antop.billiardslove.service.ContestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestRankApi {
    private final ContestService contestService;

    @GetMapping("/api/v1/contest/{id}/rank")
    public List<ContestRank> ranks(@PathVariable(name = "id") long contestId) {
        List<ContestRankDto> ranks = contestService.getRanks(contestId);
        return ranks.stream().map(this::convert).collect(Collectors.toList());
    }

    private ContestRank convert(ContestRankDto dto) {
        ContestRankDto.Participant participant = dto.getParticipant();
        return ContestRank.builder()
                .rank(dto.getRank())
                .progress(dto.getProgress())
                .score(dto.getScore())
                .participant(ContestRank.Participant.builder()
                        .id(participant.getId())
                        .name(participant.getName())
                        .handicap(participant.getHandicap())
                        .build())
                .build();
    }

    /**
     * 대회 순위 응답 클래스
     *
     * @author jammini
     */
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    static class ContestRank {
        /**
         * 순위
         */
        private final Integer rank;
        /**
         * 참가자
         */
        private final Participant participant;
        /**
         * 진행률
         */
        private final Integer progress;
        /**
         * 점수
         */
        private final Integer score;

        @Getter
        @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
        @Builder
        static class Participant {
            /**
             * 참가자 아이디
             */
            private final long id;
            /**
             * 참가자명
             */
            private final String name;
            /**
             * 참가자 핸디캡
             */
            private final Integer handicap;
        }

    }

}

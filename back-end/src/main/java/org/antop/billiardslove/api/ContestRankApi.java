package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.service.ContestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContestRankApi {
    private final ContestService contestService;

    @GetMapping("/api/v1/contest/{id}/ranks")
    public List<Rank> ranks(@PathVariable(name = "id") long contestId) {
        Contest contest = contestService.getContest(contestId);

        Comparator<Player> compare = Comparator
                .comparing((Player p) -> ofNullable(p.getRank()).orElse(Integer.MAX_VALUE))
                .thenComparing((Player p) -> ofNullable(p.getNumber()).orElse(Integer.MAX_VALUE));

        return contest.getPlayers().stream()
                .sorted(compare)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Rank convert(Player p) {
        return Rank.builder()
                .rank(p.getRank())
                .progress(0) // TODO: 진행률
                .score(p.getScore())
                .participant(Rank.Participant.builder()
                        .id(p.getMember().getId())
                        .name(p.getMember().getNickname())
                        .handicap(p.getHandicap())
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
    static class Rank {
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

package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.service.ContestService;
import org.antop.billiardslove.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class ContestInfoApi {
    private final ContestService contestService;
    private final MemberService memberService;

    @GetMapping("/api/v1/contest/{id}")
    public ContestDto info(@PathVariable(name = "id") long contestId, @AuthenticationPrincipal Long memberId) {
        Member member = memberService.getMember(memberId);
        Contest contest = contestService.getContest(contestId);
        return convert(contest, member);
    }

    @GetMapping("/api/v1/contests")
    public List<ContestDto> list(@AuthenticationPrincipal Long memberId) {
        Member member = memberService.getMember(memberId);
        List<Contest> contests = contestService.getAllContests();
        return contests.stream().map(c -> convert(c, member)).collect(Collectors.toList());
    }

    private ContestDto convert(final Contest contest, final Member member) {
        return ContestDto.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .start(ContestDto.Start.builder()
                        .startDate(contest.getStartDate())
                        .startTime(contest.getStartTime())
                        .build())
                .end(ContestDto.End.builder()
                        .endDate(contest.getEndDate())
                        .endTime(contest.getEndTime())
                        .build())
                .state(ContestDto.State.builder()
                        .code(contest.getState().getCode())
                        // TODO: 이름 찾아야 한다.
                        .name(contest.getState().name())
                        .build())
                .maximumParticipants(contest.getMaximumParticipants())
                .participation(contest.isParticipated(member))
                .build();
    }

    /**
     * 대회 정보 응답 클래스
     *
     * @author jammini
     */
    @Getter
    @AllArgsConstructor
    @Builder
    static class ContestDto {
        /**
         * 대회 아이디
         */
        private final Long id;
        /**
         * 대회명
         */
        private final String name;
        /**
         * 대회 설명
         */
        private final String description;
        /**
         * 시작 일시
         */
        private final Start start;
        /**
         * 종료 일시
         */
        private final End end;
        /**
         * 진행 상태
         */
        private final State state;
        /**
         * 최대 참가 인원
         */
        private final Integer maximumParticipants;
        /**
         * 조회한 회원의 참가 여부
         */
        private final boolean participation;

        @Getter
        @Builder
        @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
        static class Start {
            /**
             * 시작일
             */
            private final LocalDate startDate;
            /**
             * 시작시간
             */
            private final LocalTime startTime;
        }

        @Getter
        @Builder
        @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
        static class End {
            /**
             * 종료일
             */
            private final LocalDate endDate;
            /**
             * 종료시간
             */
            private final LocalTime endTime;
        }

        @Getter
        @Builder
        @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
        static class State {
            /**
             * 상태 코드
             */
            private final String code;
            /**
             * 상태명
             */
            private final String name;
        }
    }

}

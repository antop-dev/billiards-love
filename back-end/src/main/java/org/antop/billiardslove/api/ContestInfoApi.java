package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.service.ContestService;
import org.antop.billiardslove.service.MemberService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 대회 정보 한건 조회
     */
    @GetMapping("/api/v1/contest/{id}")
    public ContestInfoVo info(@PathVariable(name = "id") long contestId, @AuthenticationPrincipal Long memberId) {
        final Member member = memberService.getMember(memberId);
        Contest contest = contestService.getContest(contestId);
        return convert(contest, member);
    }

    /**
     * 대회 목록 조회
     */
    @GetMapping("/api/v1/contests")
    public List<ContestInfoVo> list(@AuthenticationPrincipal Long memberId) {
        final Member member = memberService.getMember(memberId);
        List<Contest> contests = contestService.getAllContests();
        return contests.stream().map(c -> convert(c, member)).collect(Collectors.toList());
    }

    /**
     * 대회 등록
     */
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PostMapping("/api/v1/contest")
    public ContestVo register(@RequestBody ContestRegistrationRequest request) {
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
        return convert(contest);
    }

    /**
     * 대회 정보 수정
     */
    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PutMapping("/api/v1/contest/{id}")
    public ContestVo modify(@PathVariable(name = "id") long contestId,
                            @RequestBody ContestModifyRequest request) {
        ContestDto contestDto = ContestDto.builder()
                .title(request.getName())
                .description(request.getDescription())
                .startDate(request.getStart().getStartDate())
                .startTime(request.getStart().getStartTime())
                .endDate(request.getEnd().getEndDate())
                .endTime(request.getEnd().getEndTime())
                .maxJoiner(request.getMaxJoiner())
                .build();

        Contest contest = contestService.modify(contestId, contestDto);
        return convert(contest);
    }

    private ContestVo convert(final Contest contest) {
        return ContestVo.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .start(dateAndTime(contest.getStartDate(), contest.getStartTime()))
                .end(dateAndTime(contest.getEndDate(), contest.getEndTime()))
                .state(codeAndName(contest.getState().getCode(), contest.getState().name()))
                .maxJoiner(contest.getMaxJoiner())
                .build();
    }

    private ContestInfoVo convert(final Contest contest, final Member member) {
        return ContestInfoVo.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .start(dateAndTime(contest.getStartDate(), contest.getStartTime()))
                .end(dateAndTime(contest.getEndDate(), contest.getEndTime()))
                .state(codeAndName(contest.getState().getCode(), contest.getState().name()))
                .maxJoiner(contest.getMaxJoiner())
                .joined(contest.isJoined(member))
                .build();
    }

    private DateAndTime dateAndTime(LocalDate date, LocalTime time) {
        return DateAndTime.builder().date(date).time(time).build();
    }

    private CodeAndName codeAndName(String code, String name) {
        return CodeAndName.builder().code(code).name(name).build();
    }

    /**
     * 대회 정보
     *
     * @author antop
     */
    @Getter
    @RequiredArgsConstructor
    @SuperBuilder
    static class ContestVo {
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
        private final DateAndTime start;
        /**
         * 종료 일시
         */
        private final DateAndTime end;
        /**
         * 진행 상태
         */
        private final CodeAndName state;
        /**
         * 최대 참가 인원
         */
        private final Integer maxJoiner;

    }

    /**
     * 조회용 대회 정보
     *
     * @author antop
     */
    @Getter
    @SuperBuilder
    static class ContestInfoVo extends ContestVo {
        /**
         * 조회한 회원의 참가 여부
         */
        private final boolean joined;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    static class DateAndTime {
        /**
         * 날짜
         */
        private final LocalDate date;
        /**
         * 시간
         */
        private final LocalTime time;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    static class CodeAndName {
        /**
         * 코드
         */
        private final String code;
        /**
         * 이름
         */
        private final String name;
    }

    /**
     * 대회 등록 요청
     *
     * @author jammini
     */
    @Getter
    @NoArgsConstructor
    @ToString
    static class ContestRegistrationRequest {
        /**
         * 대회명
         */
        private String name;
        /**
         * 대회 설명
         */
        private String description;
        /**
         * 시작 일시
         */
        private ContestRegistrationRequest.Start start;
        /**
         * 종료 일시
         */
        private ContestRegistrationRequest.End end;
        /**
         * 최대 참가 인원
         */
        private Integer maxJoiner;

        @Getter
        static class Start {
            /**
             * 시작일
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
            private LocalDate startDate;
            /**
             * 시작시간
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
            private LocalTime startTime;
        }

        @Getter
        static class End {
            /**
             * 종료일
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
            private LocalDate endDate;
            /**
             * 종료시간
             */
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
            private LocalTime endTime;
        }
    }

    /**
     * 대회 정보 수정 요청
     *
     * @author jammini
     */
    static class ContestModifyRequest extends ContestRegistrationRequest {
    }

}

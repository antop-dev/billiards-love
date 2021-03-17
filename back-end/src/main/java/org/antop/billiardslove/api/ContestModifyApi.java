package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.antop.billiardslove.config.security.JwtAuthenticationToken;
import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.service.ContestService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@RestController
public class ContestModifyApi {
    private final ContestService contestService;

    @Secured(JwtAuthenticationToken.ROLE_MANAGER)
    @PutMapping("/api/v1/contest/{id}")
    public Response modify(@PathVariable(name = "id") long contestId, @RequestBody Request request) {

        ContestDto contestDto = ContestDto.builder()
                .title(request.getName())
                .description(request.getDescription())
                .startDate(request.getStart().getStartDate())
                .startTime(request.getStart().getStartTime())
                .endDate(request.getEnd().getEndDate())
                .endTime(request.getEnd().getEndTime())
                .maximumParticipants(request.getMaximumParticipants())
                .build();

        Contest contest = contestService.modify(contestId, contestDto);

        return Response.builder()
                .id(contest.getId())
                .name(contest.getTitle())
                .description(contest.getDescription())
                .start(ContestRegistrationApi.Start.builder()
                        .startDate(contest.getStartDate())
                        .startTime(contest.getStartTime())
                        .build())
                .end(ContestRegistrationApi.End.builder()
                        .endDate(contest.getEndDate())
                        .endTime(contest.getEndTime())
                        .build())
                .state(ContestRegistrationApi.State.builder()
                        .code(contest.getState().getCode())
                        .name(contest.getState().name())
                        .build())
                .maximumParticipants(contest.getMaximumParticipants())
                .build();
    }

    /**
     * 대회 정보 요청
     *
     * @author jammini
     */
    @Getter
    @NoArgsConstructor
    @ToString
    static class Request {
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
        private ContestRegistrationApi.Request.Start start;
        /**
         * 종료 일시
         */
        private ContestRegistrationApi.Request.End end;
        /**
         * 최대 참가 인원
         */
        private Integer maximumParticipants;

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
     * 대회 정보 응답
     *
     * @author jammini
     */
    @Getter
    @ToString
    @AllArgsConstructor
    @Builder
    static class Response {
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
        private final ContestRegistrationApi.Start start;
        /**
         * 종료 일시
         */
        private final ContestRegistrationApi.End end;
        /**
         * 진행 상태
         */
        private final ContestRegistrationApi.State state;
        /**
         * 최대 참가 인원
         */
        private final Integer maximumParticipants;

    }

    @Getter
    @Builder
    static class Start {
        /**
         * 시작일
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private final LocalDate startDate;
        /**
         * 시작시간
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
        private final LocalTime startTime;
    }

    @Getter
    @Builder
    static class End {
        /**
         * 종료일
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private final LocalDate endDate;
        /**
         * 종료시간
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
        private final LocalTime endTime;
    }

    @Getter
    @Builder
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

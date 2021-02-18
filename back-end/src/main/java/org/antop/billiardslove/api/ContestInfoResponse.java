package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 대회 정보 응답 클래스
 *
 * @author jammini
 */
@Getter
public class ContestInfoResponse {
    /**
     * 대회 아이디
     */
    private Long id;
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
    private Start start;
    /**
     * 종료 일시
     */
    private End end;
    /**
     * 진행 상태
     */
    private State state;
    /**
     * 최대 참가 인원
     */
    private Integer maximumParticipants;

    @Builder
    public ContestInfoResponse(Long id, String name, String description, Start start, End end, State state, Integer maximumParticipants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.state = state;
        this.maximumParticipants = maximumParticipants;
    }

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

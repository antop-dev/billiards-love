package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 대회 정보 응답
 *
 * @author jammini
 */
@Getter
@ToString
@AllArgsConstructor
@Builder
public class ContestResponse {
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
    private final Integer maxJoiner;


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

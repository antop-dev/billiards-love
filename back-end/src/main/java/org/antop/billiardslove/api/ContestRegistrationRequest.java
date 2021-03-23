package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 대회 정보 요청
 *
 * @author jammini
 */
@Getter
@NoArgsConstructor
@ToString
public class ContestRegistrationRequest {
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

package org.antop.billiardslove.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 대회 정보 요청
 *
 * @author jammini
 */
@Getter
@NoArgsConstructor
@ToString
public class ContestRegisRequest {

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
    private Integer maximumParticipants;

    @Getter
    static class Start {
        /**
         * 시작일
         */
        private String startDate;
        /**
         * 시작시간
         */
        private String startTime;
    }

    @Getter
    static class End {
        /**
         * 종료일
         */
        private String endDate;
        /**
         * 종료시간
         */
        private String endTime;
    }
}

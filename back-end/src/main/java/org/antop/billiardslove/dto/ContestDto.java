package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContestDto {
    /**
     * 대회명
     */
    private final String title;
    /**
     * 대회 설명
     */
    private final String description;
    /**
     * 시작일
     */
    private final String startDate;
    /**
     * 시작시간
     */
    private final String startTime;
    /**
     * 종료일
     */
    private final String endDate;
    /**
     * 종료시간
     */
    private final String endTime;
    /**
     * 상태 코드
     */
    private final String code;
    /**
     * 상태명
     */
    private final String name;
    /**
     * 최대 참가 인원
     */
    private final Integer maximumParticipants;
}

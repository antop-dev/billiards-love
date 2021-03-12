package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private final LocalDate startDate;
    /**
     * 시작시간
     */
    private final LocalTime startTime;
    /**
     * 종료일
     */
    private final LocalDate endDate;
    /**
     * 종료시간
     */
    private final LocalTime endTime;
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

package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ContestDto {
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
     * 시작일
     */
    private LocalDate startDate;
    /**
     * 시작시간
     */
    private LocalTime startTime;
    /**
     * 종료일
     */
    private LocalDate endDate;
    /**
     * 종료시간
     */
    private LocalTime endTime;
    /**
     * 상태 코드
     */
    private String code;
    /**
     * 상태명
     */
    private String state;
    /**
     * 최대 참가 인원
     */
    private Integer maximumParticipants;

    @Builder
    public ContestDto(Long id, String name, String description, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String code, String state, Integer maximumParticipants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.code = code;
        this.state = state;
        this.maximumParticipants = maximumParticipants;
    }
}

package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@ToString
@FieldNameConstants
public class ContestDto {
    /**
     * 대회 아이디
     */
    private final Long id;
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
     * 최대 참가 인원
     */
    private final Integer maxJoiner;
    /**
     * 대회 상태 코드
     */
    private final String stateCode;
    /**
     * 대회 상태명
     */
    private final String stateName;
    /**
     * 내 선수 정보<br>
     * 회원이 대회에 참가하지 않았을 경우 null
     */
    private final PlayerDto player;

}

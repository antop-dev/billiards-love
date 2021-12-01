package org.antop.billiardslove.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder
@FieldNameConstants
public class MatchPlayerDto extends PlayerDto {
    /**
     * 경기 결과
     */
    private final String[] result;

}

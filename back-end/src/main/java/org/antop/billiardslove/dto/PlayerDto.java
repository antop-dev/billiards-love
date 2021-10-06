package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder
@RequiredArgsConstructor
@ToString
@FieldNameConstants
public class PlayerDto {
    /**
     * 참가자 아이디
     */
    private final long id;
    /**
     * 참가자 번호
     */
    private final Integer number;
    /**
     * 참가자 핸디캡
     */
    private final int handicap;
    /**
     * 참가자 별명
     */
    private final String nickname;
    /**
     * 순위
     */
    private final Integer rank;
    /**
     * 점수
     */
    private final Integer score;
    /**
     * 순위 변동 현황<br>
     * 양수: 올라감
     * 0: 그대로
     * 음수: 내려감
     */
    private final int variation;
    /**
     * 진행률 (%)
     */
    private final int progress;
}

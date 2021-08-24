package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/**
 * 경기 정보 데이터
 *
 * @author antop
 */
@RequiredArgsConstructor
@Getter
@ToString
@Builder
@FieldNameConstants
public class MatchDto {
    /**
     * 경기 아이디
     */
    private final long id;
    /**
     * 상대 선수 정보
     */
    private final PlayerDto opponent;
    /**
     * 경기 결과 (크기: 3)
     */
    private final String[] result;
    /**
     * 확정 여부
     */
    private final boolean closed;

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class Opponent {
        /**
         * 선수 아이디
         */
        private final long id;
        /**
         * 선수 번호
         */
        private final long number;
        /**
         * 선수 별명
         */
        private final String nickname;
    }

}

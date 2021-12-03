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
     * 회원이 조회 시 : 내 정보<br>
     * 관리자가 조회 시 : 왼쪽 선수 정보
     */
    private final MatchPlayerDto left;
    /**
     * 회원이 조회 시 : 상대 선수 정보<br>
     * 관리자가 조회 시 : 오른쪽 선수 정보
     */
    private final MatchPlayerDto right;
    /**
     * 확정 여부
     */
    private final boolean closed;

}

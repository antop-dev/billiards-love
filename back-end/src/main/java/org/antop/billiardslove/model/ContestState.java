package org.antop.billiardslove.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 대회 진행 상태
 *
 * @author jammini
 */
@Getter
@RequiredArgsConstructor
public enum ContestState {
    /**
     * 진행중
     */
    PROCEEDING("진행중"),
    /**
     * 접수중 (시작하지 않음)
     */
    ACCEPTING("접수중"),
    /**
     * 준비중
     */
    PREPARING("준비중"),
    /**
     * 중지됨
     */
    STOPPED("중지"),
    /**
     * 종료
     */
    END("종료");

    private final String label;

}

package org.antop.billiardslove.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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
    PROCEEDING("0"),
    /**
     * 접수중 (시작하지 않음)
     */
    ACCEPTING("1"),
    /**
     * 준비중
     */
    PREPARING("2"),
    /**
     * 중지됨
     */
    STOPPED("3"),
    /**
     * 종료
     */
    END("4");

    private final String code;

    public static ContestState of(String code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

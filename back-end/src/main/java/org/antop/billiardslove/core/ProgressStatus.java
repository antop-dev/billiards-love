package org.antop.billiardslove.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 대회 진행 상태
 *
 * @author
 */
@Getter
@RequiredArgsConstructor
public enum ProgressStatus {
    /**
     * 접수중 (시작하지 않음)
     */
    NONE("0"),
    /**
     * 진행중
     */
    PROGRESS("1"),
    /**
     * 종료
     */
    END("2");
    /**
     * 코드값
     */
    private final String code;

    public static ProgressStatus of(String code) {
        return Arrays.stream(values())
                .filter(it -> it.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(code));
    }
}

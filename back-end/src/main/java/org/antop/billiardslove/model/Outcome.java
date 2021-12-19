package org.antop.billiardslove.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Outcome {
    /**
     * 승
     */
    WIN("W", 3),
    /**
     * 패
     */
    LOSE("L", 1),
    /**
     * 기권
     */
    ABSTENTION("A", -3),
    /**
     * 진행의사 있음
     */
    HOLD("H", -1),
    /**
     * 입력되지 않음
     */
    NONE("N", 0);

    /**
     * 코드
     */
    private final String code;
    /**
     * 점수
     */
    private final int score;

    public static Outcome of(String code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}

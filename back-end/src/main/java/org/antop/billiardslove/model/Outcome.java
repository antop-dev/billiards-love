package org.antop.billiardslove.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.IntFunction;

@AllArgsConstructor
@Getter
public enum Outcome {
    /**
     * 승
     */
    WIN("W", score -> score + 3),
    /**
     * 패
     */
    LOSE("L", score -> score + 1),
    /**
     * 기권
     */
    ABSTENTION("A", score -> score - 3),
    /**
     * 진행의사 있음
     */
    HOLD("H", score -> score - 1),
    /**
     * 입력되지 않음
     */
    NONE("N", score -> score);

    /**
     * 코드
     */
    private final String code;
    /**
     * 점수
     */
    private final IntFunction<Integer> scoreFunc;

    public static Outcome of(String code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

}

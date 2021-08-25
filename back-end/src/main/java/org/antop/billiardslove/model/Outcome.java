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
    WIN("W"),
    /**
     * 패
     */
    LOSE("L"),
    /**
     * 기권
     */
    ABSTENTION("A"),
    /**
     * 진행의사 있음
     */
    HOLD("H"),
    /**
     * 입력되지 않음
     */
    NONE("N");

    private final String code;

    public static Outcome of(String code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}

package org.antop.billiardslove.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게임결과 상태
 *
 * @author jammini
 */
@Getter
@AllArgsConstructor
public enum GameResultStatus {
    /**
     * 입력되지 않음
     */
    NONE("0"),
    /**
     * 승리
     */
    WIN("1"),
    /**
     * 패배
     */
    LOSE("2"),
    /**
     * 기권
     */
    ABSTENTION("3"),
    /**
     * 진행의사 있음
     */
    HOLD("4");

    private String status;

    public static GameResultStatus of(String code) {
        for (GameResultStatus o : values()) {
            if (o.getStatus().equals(code)) {
                return o;
            }
        }
        throw new IllegalArgumentException(code);
    }
}

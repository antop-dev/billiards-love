package org.antop.billiardslove.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 대회 진행 상태
 *
 * @author jammini
 */
@Getter
@RequiredArgsConstructor
public enum ProgressStatus {
    /**
     * 접수중 (시작하지 않음)
     */
    ACCEPTING("0"),
    /**
     * 진행중
     */
    PROCEEDING("1"),
    /**
     * 종료
     */
    END("2");
    /**
     * 코드값
     */
    private final String code;

    public static ProgressStatus of(String code) {
        for (ProgressStatus o : values()) {
            if (o.getCode().equals(code))
                return o;
        }
        return ProgressStatus.ACCEPTING;
    }
}

package org.antop.billiardslove.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgressStatus {

    /** 접수중(시작하지 않음) */
    NONE("0"),
    /** 진행중 */
    PROGRESS("1"),
    /** 종료 */
    END("2");

    private String status;

    public static ProgressStatus of(String code){
        for (ProgressStatus o : values()){
            if (o.getStatus().equals(code)){
                return o;
            }
        }
        throw new IllegalArgumentException(code);
    }
}

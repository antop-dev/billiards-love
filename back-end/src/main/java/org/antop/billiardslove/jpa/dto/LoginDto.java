package org.antop.billiardslove.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    /**
     * 새로 발급된 JWT토큰
     */
    private String token;
    /**
     * 처음 가입 여부
     */
    private boolean first;
}

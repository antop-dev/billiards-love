package org.antop.billiardslove.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String token;
    private boolean first;
}

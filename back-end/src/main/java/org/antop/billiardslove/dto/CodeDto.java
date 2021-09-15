package org.antop.billiardslove.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class CodeDto {
    /**
     * 코드 그룹
     */
    private final String group;
    /**
     * 코드
     */
    private final String code;
    /**
     * 코드명
     */
    private final String name;
}

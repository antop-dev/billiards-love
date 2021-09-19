package org.antop.billiardslove.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 경기 결과
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchResult {
    /**
     * 결과가 입력되지 않은 경기 결과
     */
    public static final MatchResult NONE = MatchResult.of(Outcome.NONE, Outcome.NONE, Outcome.NONE);
    /**
     * 첫번째 경기
     */
    private final Outcome first;
    /**
     * 두번째 경기
     */
    private final Outcome second;
    /**
     * 세번째 경기
     */
    private final Outcome third;

    /**
     * 배열로 리턴
     *
     * @return 크기 3의 배열
     */
    public Outcome[] toArray() {
        return new Outcome[]{first, second, third};
    }

    public static MatchResult of(Outcome first, Outcome second, Outcome third) {
        return new MatchResult(first, second, third);
    }

}

package org.antop.billiardslove.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * 경기 결과
 */
@Getter
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

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchResult that = (MatchResult) o;
        return first == that.first && second == that.second && third == that.third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}

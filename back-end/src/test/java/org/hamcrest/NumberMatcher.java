package org.hamcrest;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * JSON 값 비교 시 타입(Integer/Double/Long 등등) 구분 없이 비교하기 위해서 사용한다.<br>
 * 예를 들어 { "n": 111 } 의 경우 비교시에 Integer 형을 사용하고 실제 클래스에서는 Long 형을 사용할 경우<br>
 * 111 != 111L 결과가 나오게 된다.<br>
 * 이 부분을 해결해주는 매처이다.
 *
 * @author antop
 */
public class NumberMatcher extends TypeSafeMatcher<Number> {
    private final Number value;

    private NumberMatcher(Number value) {
        this.value = value;
    }

    public static NumberMatcher is(Number number) {
        return new NumberMatcher(Objects.requireNonNull(number));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(value.toString());
    }

    @Override
    protected boolean matchesSafely(Number item) {
        if (item == null) return false;
        BigDecimal x = new BigDecimal(value.toString());
        BigDecimal y = new BigDecimal(item.toString());
        return x.compareTo(y) == 0;
    }

}

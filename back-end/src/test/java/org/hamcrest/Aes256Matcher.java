package org.hamcrest;

import org.antop.billiardslove.util.Aes256Util;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * AES256 암호화된 값을 비교한다.
 *
 * @author antop
 */
public class Aes256Matcher extends TypeSafeMatcher<String> {
    private final String value;
    private final String secretKey;

    private Aes256Matcher(String value, String secretKey) {
        this.value = value;
        this.secretKey = secretKey;
    }

    /**
     * 매처 생성
     *
     * @param value     비교할 값
     * @param secretKey AES256 암호화 키
     */
    public static Aes256Matcher is(String value, String secretKey) {
        return new Aes256Matcher(Objects.requireNonNull(value), Objects.requireNonNull(secretKey));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(value);
    }

    @Override
    protected boolean matchesSafely(String item) {
        if (item == null) return false;
        return StringUtils.equals(value, Aes256Util.decrypt(item, secretKey));
    }

}

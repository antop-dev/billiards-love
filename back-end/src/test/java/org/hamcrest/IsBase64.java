package org.hamcrest;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64로 인코딩된 문자열인지 체크한다.<br>
 * https://www.baeldung.com/hamcrest-custom-matchers
 *
 * @author antop
 */
public class IsBase64 extends TypeSafeMatcher<String> {
    @Override
    protected boolean matchesSafely(String item) {
        return Base64.isBase64(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("only base64 encoded string");
    }

    public static Matcher<String> isBase64() {
        return new IsBase64();
    }
}

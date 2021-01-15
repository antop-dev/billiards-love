package org.hamcrest;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * 정상적인 JWT 토큰인지 체크한다.<br>
 *
 * @author antop
 */
public class IsJwtToken extends TypeSafeMatcher<String> {
    private final String key;

    public IsJwtToken(String key) {
        this.key = key;
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(item);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("JWT token string");
    }

    public static Matcher<String> isJwtToken(String key) {
        return new IsJwtToken(key);
    }
}

package org.springframework.security.crypto.password;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 스프링 시큐리티에서 사용하는 Password Encoder 를 사용해서 값을 뽑아내기 위한 테스트 클래스
 *
 * @author antop
 */
class PasswordEncoderTest {
    private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void encode() {
        String plain = "P@ssw0rd";
        String encoded = encoder.encode(plain);
        System.out.println("encoded = " + encoded);
        // 같은 텍스트를 인코딩 할때마다 다른 값이 나온다.
        assertThat(encoded, not(is(encoder.encode(plain))));
        // matches() 메서드를 이용해서 비교해야 한다.
        assertTrue(encoder.matches("P@ssw0rd", encoded));
        assertFalse(encoder.matches("p@ssw0rd", encoded));
    }
}

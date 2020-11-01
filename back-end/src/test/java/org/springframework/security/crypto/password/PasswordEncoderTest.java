package org.springframework.security.crypto.password;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class PasswordEncoderTest {
    private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void encode() {
        String plain = "P@ssw0rd";
        String encoded = encoder.encode(plain);
        System.out.println("encoded = " + encoded);
    }
}

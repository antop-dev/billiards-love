package org.antop.billiardslove.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Aes256UtilTest {

    @DisplayName("정상적으로 AES256 암호화한다.")
    @Test
    void encrypt() throws Exception {
        String text = "값을 암호화 합니다.";
        String key = generateKey();

        String encrypted = Aes256Util.encrypt(text, key);
        String decrypted = Aes256Util.decrypt(encrypted, key);
        assertThat(text, is(decrypted));
    }

    @DisplayName("키의 자릿수가 모자를 경우도 된다.")
    @Test
    void name() throws Exception {
        String text = "키의 자리수가 32바이트(256bit)가 안된다.";
        String key = generateKey().substring(0, 10);

        String encrypted = Aes256Util.encrypt(text, key);
        String decrypted = Aes256Util.decrypt(encrypted, key);
        assertThat(text, is(decrypted));
    }

    @DisplayName("키의 자릿수가 넘칠 경우")
    @Test
    void name1() throws Exception {
        String text = "키의 자리수가 37바이트이다.";
        String key = generateKey() + generateKey().substring(0, 5);

        String encrypted = Aes256Util.encrypt(text, key);
        String decrypted = Aes256Util.decrypt(encrypted, key);
        assertThat(text, is(decrypted));
    }

    private String generateKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}

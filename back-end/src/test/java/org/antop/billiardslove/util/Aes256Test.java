package org.antop.billiardslove.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Aes256Test {

    @DisplayName("암호화 키를 생성한다.")
    @Test
    void generateKey() {
        String key = Aes256.generateKey();
        Assertions.assertTrue(Base64.isBase64(key));
    }

    @DisplayName("암복호화한다.")
    @Test
    void encrypt() {
        String text = "값을 암호화 합니다.";
        String key = Aes256.generateKey();
        String encrypted = Aes256.encrypt(text, key);
        String decrypted = Aes256.decrypt(encrypted, key);
        assertThat(text, is(decrypted));
    }

    @DisplayName("프론트에서 생성한 암호화 값을 복호화한다.")
    @Test
    void decryptByFrontEnd() {
        String key = "L1X02XNZXqkMBlLNvDfheNnPOhDyWEJz4XCSgysX9bc=";
        String encrypted = "J61Mk1UZ71Tk+aTYkwE8RXKEpXtG8x5Hl/dBCMkt8bHSGsQOEFVksjN9IEratTfRxOaxJicqZQ==";
        String decrypted = Aes256.decrypt(encrypted, key);
        assertThat(decrypted, is("드디어 AES256을 했다."));
    }

}

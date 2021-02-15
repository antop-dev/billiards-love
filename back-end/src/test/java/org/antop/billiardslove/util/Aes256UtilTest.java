package org.antop.billiardslove.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Aes256UtilTest {

    @Test
    void generateKey() {
        String key = Aes256Util.generateKey();
        Assertions.assertTrue(Base64.isBase64(key));
    }

    @Test
    void encrypt() {
        String text = "값을 암호화 합니다.";
        String key = Aes256Util.generateKey();
        String encrypted = Aes256Util.encrypt(text, key);
        String decrypted = Aes256Util.decrypt(encrypted, key);
        assertThat(text, is(decrypted));
    }

    @Test
    void decryptByFrontEnd() {
        String key = "L1X02XNZXqkMBlLNvDfheNnPOhDyWEJz4XCSgysX9bc=";
        String encrypted = "J61Mk1UZ71Tk+aTYkwE8RXKEpXtG8x5Hl/dBCMkt8bHSGsQOEFVksjN9IEratTfRxOaxJicqZQ==";
        String decrypted = Aes256Util.decrypt(encrypted, key);
        assertThat(decrypted, is("드디어 AES256을 했다."));
    }

}

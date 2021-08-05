package org.antop.billiardslove;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

/**
 * Jasypt 라이브러리는 이용해서 값을 암호화 하는 클래스다.
 *
 * @author antop
 */
class JasyptTest {
    /**
     * 암복호화에 사용되는 키
     */
    private final static String PASSWORD = "abcdefg";
    /**
     * 암호화 하려는 값<br>
     * 암호화 한 값은 application.properties 에서 "ENC(암호화된 값)"으로 사용된다.
     */
    private final static String[] PLAIN_TEXTS = {"hello world!"};

    @Test
    void encryptPlainText() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(PASSWORD);

        for (String plainText : PLAIN_TEXTS) {
            String encrypted = encryptor.encrypt(plainText);
            System.out.println(plainText + " -> " + encrypted);
        }


    }
}

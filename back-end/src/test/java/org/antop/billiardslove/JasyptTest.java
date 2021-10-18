package org.antop.billiardslove;

import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Jasypt 라이브러리는 이용해서 값을 암호화 하는 클래스다.
 *
 * @author antop
 */
class JasyptTest {
    /**
     * 기본값들이 들어있는 프로퍼티 클래스
     */
    private final JasyptEncryptorConfigurationProperties properties = new JasyptEncryptorConfigurationProperties();
    /**
     * 암복호화에 사용되는 키<br>
     * Password is not ASCII
     */
    private final static String PASSWORD = "PASSWORD";
    /**
     * 암호화 하려는 값<br>
     * 암호화 한 값은 application.properties 에서 "ENC(암호화된 값)"으로 사용된다.
     */
    private final static String[] PLAIN_TEXTS = {"hello world"};

    @Test
    void encryptAndDecrypt() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD);
        config.setStringOutputType(properties.getStringOutputType());
        config.setAlgorithm(properties.getAlgorithm());
        config.setKeyObtentionIterations(properties.getKeyObtentionIterations());
        config.setSaltGeneratorClassName(properties.getSaltGeneratorClassname());
        config.setIvGeneratorClassName(properties.getIvGeneratorClassname());
        config.setPoolSize(properties.getPoolSize());
        encryptor.setConfig(config);

        for (String plainText : PLAIN_TEXTS) {
            String encrypted = encryptor.encrypt(plainText);
            System.out.println(plainText + " -> " + encrypted);
            String decrypted = encryptor.decrypt(encrypted);
            assertThat(decrypted, is(plainText));
        }

    }
}

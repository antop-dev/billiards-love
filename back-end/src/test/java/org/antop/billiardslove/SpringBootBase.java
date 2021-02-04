package org.antop.billiardslove;

import com.github.javafaker.Faker;
import org.antop.billiardslove.constant.Profiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

/**
 * 스프링 부트 테스트 공통 클래스
 *
 * @author antop
 */
@SpringBootTest
@ActiveProfiles(Profiles.TEST)
public class SpringBootBase {
    /**
     * https://github.com/DiUS/java-faker
     */
    protected final Faker faker = new Faker(Locale.KOREAN);
}

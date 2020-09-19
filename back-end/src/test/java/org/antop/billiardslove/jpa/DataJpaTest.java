package org.antop.billiardslove.jpa;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

/**
 * Repository를 테스트 하기 위한 부모 클래스
 *
 * @author antop
 */
@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
@OverrideAutoConfiguration(enabled = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract public class DataJpaTest {
    /**
     * 로깅
     */
    protected static final Logger log = LoggerFactory.getLogger("DataJpaTest");
    /**
     * 가짜 데이터 생성기
     */
    protected final Faker faker = new Faker(Locale.KOREA);
    /**
     * 가짜 데이터 생성기2
     */
    protected final FakeValuesService fakeValues =
            new FakeValuesService(Locale.KOREA, new RandomService());
    /**
     * JPA
     */
    @PersistenceContext
    private EntityManager em;

    public final void flush() {
        log.info("before flush...");
        em.flush();
        log.info("after flush...");
    }

    @AfterEach
    public void tearDown() {
        flush();
    }

}

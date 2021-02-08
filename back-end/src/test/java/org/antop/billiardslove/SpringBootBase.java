package org.antop.billiardslove;

import com.github.javafaker.Faker;
import org.antop.billiardslove.constant.Profiles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

/**
 * 스프링 부트 테스트 공통 클래스
 *
 * @author antop
 */
@SpringBootTest
@ActiveProfiles(Profiles.TEST)
@Transactional
@AutoConfigureMockMvc
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@OverrideAutoConfiguration(enabled = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class SpringBootBase {
    /**
     * 로깅
     */
    protected static Logger log = null;

    /**
     * https://github.com/DiUS/java-faker
     */
    protected final Faker faker = new Faker(Locale.KOREAN);

    @Autowired
    protected MockMvc mockMvc;

    /**
     * JPA
     */
    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        log = LoggerFactory.getLogger(getClass());
    }

    @AfterEach
    public void tearDown() {
        flush();
    }

    final void flush() {
        log.info("before flush...");
        em.flush();
        log.info("after flush...");
    }

}

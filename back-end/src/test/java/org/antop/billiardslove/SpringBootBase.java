package org.antop.billiardslove;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.antop.billiardslove.constants.Profiles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(uriPort = 9000)
@ActiveProfiles(Profiles.TEST)
@Transactional
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test-h2.sql")
@Import({RestDocsConfig.class})
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

    @Autowired
    protected ObjectMapper objectMapper;

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
        flushAndClear();
    }

    protected final void flush() {
        log.info("before flush...");
        em.flush();
        log.info("after flush...");
    }

    protected void flushAndClear() {
        flush();
        em.clear();
    }

    /**
     * 엔티티가 영속성이 됬는지 여부
     *
     * @param entity JPA 엔티티
     * @return 영속성 여부
     */
    protected final boolean isPersisted(Object entity) {
        return em.contains(entity);
    }

}

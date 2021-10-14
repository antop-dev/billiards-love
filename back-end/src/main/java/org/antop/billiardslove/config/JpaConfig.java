package org.antop.billiardslove.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 슬라이스 테스트를 위하여 별도의 설정 클래스로 분리한다.<br>
 * https://sup2is.tistory.com/107
 * https://stackoverflow.com/questions/60606861/spring-boot-jpa-metamodel-must-not-be-empty-when-trying-to-run-junit-integrat
 *
 * @author antop
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}

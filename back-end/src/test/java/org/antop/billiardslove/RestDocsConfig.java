package org.antop.billiardslove;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.HOST;
import static org.springframework.http.HttpHeaders.PRAGMA;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeMatchingHeaders;

/**
 * Spring REST Docs 공통 설정
 *
 * @author antop
 */
@TestConfiguration
public class RestDocsConfig {
    @Bean
    RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(
                        // 포멧팅 된 JSON
                        prettyPrint(),
                        // 헤더 제거
                        removeHeaders(HOST)
                )
                // 응답 전문 포멧
                .withResponseDefaults(
                        // 포멧팅 된 JSON
                        prettyPrint(),
                        // 헤더 제거
                        removeHeaders(PRAGMA, EXPIRES, CACHE_CONTROL),
                        // 헤더 패턴 제거
                        removeMatchingHeaders(/* "X-"로 시작 */"^X-.*$")
                );
    }

}

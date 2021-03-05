package org.antop.billiardslove;

import org.antop.billiardslove.config.Profiles;
import org.antop.billiardslove.config.SecurityConfig;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * 스프링 시큐리티 필터에서 발생하는 예외는 {@link org.springframework.web.bind.annotation.ControllerAdvice} 에서 제어할 수 없다.<br>
 * 그러므로 필터 체인의 맨 앞에서 예외를 처리해주는 필터를 사용하자.
 *
 * @author antop
 */
@TestConfiguration
@Profile(Profiles.TEST)
public class TestSecurityConfig extends SecurityConfig {
    public TestSecurityConfig(JwtTokenProvider jwtTokenProvider, PrincipalProvider principalProvider) {
        super(jwtTokenProvider, principalProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        super.configure(web);
        // 추가로 아래 URL 패턴도 스프링 시큐리티를 적용하지 않는다.
        web.ignoring().antMatchers("/test/**");
    }
}

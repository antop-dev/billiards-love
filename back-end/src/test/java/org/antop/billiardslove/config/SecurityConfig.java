package org.antop.billiardslove.config;

import org.antop.billiardslove.config.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <a href="https://github.com/spring-projects/spring-security/issues/4516">SecurityMockMvcResultMatchers does not work for sessionCreationPolicy(SessionCreationPolicy.STATELESS)</a><br/>
 * 스프링 시큐리티 테스트에서는 세션을 사용한다.<br/>
 * 하지만 JWT 사용시 {@link SessionCreationPolicy#STATELESS} 설정을 사용하게 되는데 이러면 테스트가 실패하게 된다.<br/>
 * 그래서 테스트 전용으로 설정를 따로 했다.
 *
 * @author antop
 */
@TestConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v*/init", "/api/v*/logged-in")
                .permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                // 테스트 검증에는 세션이 필요함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

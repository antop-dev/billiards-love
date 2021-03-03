package org.antop.billiardslove.config;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.error.ErrorHandlerFilter;
import org.antop.billiardslove.config.security.JwtAuthenticationFilter;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
/*
 * https://www.baeldung.com/spring-security-method-security
 * The prePostEnabled property enables Spring Security pre/post annotations
 * The securedEnabled property determines if the @Secured annotation should be enabled
 * The jsr250Enabled property allows us to use the @RoleAllowed annotation
 */
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile({Profiles.LOCAL, Profiles.PRODUCTION})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalProvider principalProvider;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/v*/init", "/api/v*/logged-in");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(exceptionHandlerFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    /**
     * JWT 토큰을 분석해서 인증을 하는 필터
     */
    Filter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, principalProvider);
    }

    /**
     * 공통 예외 처리 필트
     */
    Filter exceptionHandlerFilter() {
        return new ErrorHandlerFilter();
    }

}

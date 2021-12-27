package org.antop.billiardslove.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.PrincipalProvider.Principal;
import org.antop.billiardslove.exception.MemberNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalProvider principalProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = getToken(request);
        log.debug("token = {}", token);

        // 유효한 토큰인지 확인합니다.
        if (StringUtils.isBlank(token) || !jwtTokenProvider.validateToken(token)) {
            log.debug("token is invalid.");
            throw new BadCredentialsException("인증 토큰이 유효하지 않습니다.");
        }

        try {
            String subject = jwtTokenProvider.getSubject(token);
            log.debug("subject = {}", subject);
            Principal principal = principalProvider.getPrincipal(subject);
            log.debug("principal = {}", principal);
            // Principal 로 인증 토큰 생성
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 관한 생성
            authorities.add(() -> JwtAuthenticationToken.ROLE_USER);
            if (principal.isManager()) {
                authorities.add(() -> JwtAuthenticationToken.ROLE_MANAGER);
            }
            for (GrantedAuthority authority : authorities) {
                log.debug("authority = {}", authority.getAuthority());
            }

            JwtAuthenticationToken auth = new JwtAuthenticationToken(principal.getValue(), token, authorities);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        } catch (MemberNotFoundException e) {
            // 토큰 포멧은 유효하고 사용자 아이디가 없는 경우
            throw new BadCredentialsException(e.getMessage());
        }

    }

    private String getToken(ServletRequest request) {
        return ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
    }

}

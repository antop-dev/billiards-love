package org.antop.billiardslove.config.security;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.security.PrincipalProvider.Principal;
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalProvider principalProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = getToken(request);
        // 유효한 토큰인지 확인합니다.
        if (StringUtils.isBlank(token) || !jwtTokenProvider.validateToken(token)) {
            throw new BadCredentialsException("인증 토큰이 유효하지 않습니다.");
        }

        String subject = jwtTokenProvider.getSubject(token);
        Principal principal = principalProvider.getPrincipal(subject);
        // Principal 로 인증 토큰 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 관한 생성
        authorities.add(() -> JwtAuthenticationToken.ROLE_USER);
        if (principal.isManager()) {
            authorities.add(() -> JwtAuthenticationToken.ROLE_MANAGER);
        }

        JwtAuthenticationToken auth = new JwtAuthenticationToken(principal.getValue(), token, authorities);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }

    private String getToken(ServletRequest request) {
        return ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
    }

}

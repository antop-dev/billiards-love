package org.antop.billiardslove.config.security;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.handler.MemberNotFountException;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 JWT 를 받아옵니다.
        String token = getToken(request);

        // 유효한 토큰인지 확인합니다.
        if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token)) {
            String subject = jwtTokenProvider.getSubject(token);
            long memberId = Long.parseLong(subject);

            memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFountException(memberId));

            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            JwtAuthenticationToken auth = new JwtAuthenticationToken(memberId, token);

            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private String getToken(ServletRequest request) {
        return ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
    }
}

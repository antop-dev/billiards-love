package org.antop.billiardslove.config.filter;

import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 로컬 개발 환경에서 응답을 강제로 딜레를 준다.
 *
 * @author antop
 */
public class ForceDelayFilter extends OncePerRequestFilter {
    /**
     * 3초
     */
    private static final long DELAY_MILLIS = 3_000L;

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        Thread.sleep(DELAY_MILLIS);
        filterChain.doFilter(request, response);
    }
}

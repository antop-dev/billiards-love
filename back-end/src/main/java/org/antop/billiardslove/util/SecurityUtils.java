package org.antop.billiardslove.util;

import org.antop.billiardslove.exception.NotLoginException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.antop.billiardslove.config.security.JwtAuthenticationToken.ROLE_MANAGER;

/**
 * 스프링 시큐리티 유틸리티
 */
public class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 현재 로그인된 회원의 아이디를 가져온다.
     *
     * @return 회원 아이디
     */
    public static long getMemberId() {
        return (long) getAuthentication().getPrincipal();
    }

    /**
     * 현재 로그인된 회원이 관리자인지 여부
     */
    public static boolean isManager() {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(it -> it.getAuthority().equals(ROLE_MANAGER));
    }

    private static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) throw new NotLoginException();
        return auth;
    }

}

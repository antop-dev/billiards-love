package org.antop.billiardslove.util;

import org.antop.billiardslove.exception.NotLoginException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 스프링 시큐리티 유틸리티
 */
public class SecurityUtils {

    /**
     * 현재 로그인된 회원의 아이디를 가져온다.
     *
     * @return 회원 아이디
     */
    public static long getMemberId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new NotLoginException();
        }
        return (long) authentication.getPrincipal();

    }

}

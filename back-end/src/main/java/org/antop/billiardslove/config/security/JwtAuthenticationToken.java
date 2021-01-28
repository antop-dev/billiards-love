package org.antop.billiardslove.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;

/**
 * JWT 인증 토큰
 *
 * @author antop
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 기본 롤
     */
    public static final String JWT_AUTHORITY = "ROLE_USER";
    /**
     * 회원 아이디
     */
    private final Long principal;
    private final String credentials;

    public JwtAuthenticationToken(Long principal, String credentials) {
        super(Collections.singletonList((GrantedAuthority) () -> JWT_AUTHORITY));
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    /**
     * 회원 아이디
     */
    @Override
    public Long getPrincipal() {
        return principal;
    }

    /**
     * JWR 토큰 문자열
     */
    @Override
    public String getCredentials() {
        return credentials;
    }

}

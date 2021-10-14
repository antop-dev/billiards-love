package org.antop.billiardslove.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

/**
 * JWT 인증 토큰
 *
 * @author antop
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 기본 롤
     */
    public static final String ROLE_USER = "ROLE_USER";
    /**
     * 관리자 롤
     */
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    /**
     * 회원 아이디
     */
    private final long principal;
    /**
     * JWT 토큰값
     */
    private final String credentials;

    public JwtAuthenticationToken(long principal, String credentials, Collection<GrantedAuthority> authorities) {
        super(authorities);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JwtAuthenticationToken that = (JwtAuthenticationToken) o;
        return principal == that.principal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), principal);
    }
}

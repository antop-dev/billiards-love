package org.antop.billiardslove.config.security;

/**
 * 인증 주체를 받아오는 인터페이스
 *
 * @author antop
 */
public interface PrincipalProvider {

    /**
     * 인증 주체를 얻어온다.
     *
     * @param id 고유 식별자
     * @return 인증 주체
     */
    String getPrincipal(Object id);

}

package org.antop.billiardslove.config.security;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자 권한만 혀용함
 *
 * @author antop
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured(JwtAuthenticationToken.ROLE_MANAGER)
public @interface ManagerAuthorize {

}

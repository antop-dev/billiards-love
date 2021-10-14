package org.antop.billiardslove.exception;

/**
 * 회원이 로그인하지 않은 경우
 *
 * @author antop
 */
public class NotLoginException extends RuntimeException {
    public NotLoginException() {
        super("로그인되지 않았습니다.");
    }
}

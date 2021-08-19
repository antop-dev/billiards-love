package org.antop.billiardslove.exception;

/**
 * 회원이 참여하지 않은 경기에 접근한 경우
 *
 * @author antop
 */
public class NotLoginException extends RuntimeException {
    public NotLoginException() {
        super("로그인되지 않았습니다.");
    }
}

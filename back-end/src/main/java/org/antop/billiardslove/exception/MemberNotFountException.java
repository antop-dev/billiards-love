package org.antop.billiardslove.exception;

/**
 * 회원을 찾을 수 없을 때 예외
 *
 * @author antop
 */
public class MemberNotFountException extends RuntimeException {
    public MemberNotFountException() {
        super("회원을 찾을 수 없습니다.");
    }
}

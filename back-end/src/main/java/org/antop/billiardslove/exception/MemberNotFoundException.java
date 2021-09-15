package org.antop.billiardslove.exception;

/**
 * 회원을 찾을 수 없을 때 예외
 *
 * @author antop
 */
public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}

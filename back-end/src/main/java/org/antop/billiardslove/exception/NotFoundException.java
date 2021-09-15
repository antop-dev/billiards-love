package org.antop.billiardslove.exception;

/**
 * 뭔가를 찾을 수 없을 때 예외 슈퍼 클래스
 *
 * @author antop
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("요청한 자원을 찾을 수 없습니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }

}

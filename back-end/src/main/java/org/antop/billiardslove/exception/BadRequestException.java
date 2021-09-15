package org.antop.billiardslove.exception;

/**
 * 뭔가를 잘못 요청 했을 때 예외 슈퍼 클래스
 *
 * @author antop
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("잘못된 요청입니다.");
    }

    public BadRequestException(String message) {
        super(message);
    }

}

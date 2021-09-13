package org.antop.billiardslove.exception;

/**
 * 참가 취소 불가능 예외
 *
 * @author antop
 */
public class CanNotCancelJoinException extends BadRequestException {
    public CanNotCancelJoinException() {
        super("참가 취소할 수 없습니다.");
    }
}

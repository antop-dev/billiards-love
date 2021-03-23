package org.antop.billiardslove.exception;

/**
 * 참가할 수 없는 상태일 경우 예외
 *
 * @author antop
 */
public class CanNotJoinContestStateException extends RuntimeException {
    public CanNotJoinContestStateException() {
        super("대회에 참가할 수 없는 상태입니다.");
    }
}

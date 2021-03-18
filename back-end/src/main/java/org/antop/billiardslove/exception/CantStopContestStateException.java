package org.antop.billiardslove.exception;

/**
 * 대회 상태를 변경할 수 없을 경우 예외
 *
 * @author antop
 */
public class CantStopContestStateException extends RuntimeException {
    public CantStopContestStateException() {
        super("대회를 중지할 수 없는 상태입니다.");
    }
}

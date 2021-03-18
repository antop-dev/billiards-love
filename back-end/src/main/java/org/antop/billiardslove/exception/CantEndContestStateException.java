package org.antop.billiardslove.exception;

public class CantEndContestStateException extends RuntimeException {
    public CantEndContestStateException() {
        super("대회를 종료할 수 없는 상태입니다.");
    }
}

package org.antop.billiardslove.exception;

public class CantStartContestStateException extends BadRequestException {
    public CantStartContestStateException() {
        super("대회를 시작할 수 없는 상태입니다.");
    }
}

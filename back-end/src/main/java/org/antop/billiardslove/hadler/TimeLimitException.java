package org.antop.billiardslove.hadler;

public class TimeLimitException extends RuntimeException {
    public TimeLimitException() {
        super("검증 시간을 초과하였습니다.");
    }
}

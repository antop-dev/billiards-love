package org.antop.billiardslove.exception;

public class ContestProceedingException extends BadRequestException {
    public ContestProceedingException() {
        super("진행중인 대회입니다.");
    }
}

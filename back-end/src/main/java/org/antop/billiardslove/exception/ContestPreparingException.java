package org.antop.billiardslove.exception;

public class ContestPreparingException extends BadRequestException {
    public ContestPreparingException() {
        super("준비중인 대회입니다.");
    }
}

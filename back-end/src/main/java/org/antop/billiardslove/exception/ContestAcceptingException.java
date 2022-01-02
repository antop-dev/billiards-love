package org.antop.billiardslove.exception;

public class ContestAcceptingException extends BadRequestException {
    public ContestAcceptingException() {
        super("접수중인 대회입니다.");
    }
}

package org.antop.billiardslove.exception;

/**
 * 이미 종료된 대회일 때 예외
 *
 * @author jammini
 */
public class ContestEndException extends BadRequestException {
    public ContestEndException() {
        super("종료된 대회입니다.");
    }
}

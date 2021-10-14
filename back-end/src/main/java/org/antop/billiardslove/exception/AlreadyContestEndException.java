package org.antop.billiardslove.exception;

/**
 * 이미 종료된 대회일 때 예외
 *
 * @author jammini
 */
public class AlreadyContestEndException extends BadRequestException {
    public AlreadyContestEndException() {
        super("이미 종료된 대회입니다.");
    }
}

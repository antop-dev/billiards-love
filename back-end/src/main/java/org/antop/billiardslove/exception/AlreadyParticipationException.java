package org.antop.billiardslove.exception;

/**
 * 이미 참가한 대회일 때 예외
 *
 * @author jammini
 */
public class AlreadyParticipationException extends RuntimeException {
    public AlreadyParticipationException() {
        super("이미 참가한 대회입니다.");
    }
}

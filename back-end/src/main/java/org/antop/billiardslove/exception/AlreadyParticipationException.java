package org.antop.billiardslove.exception;

public class AlreadyParticipationException extends Exception {
    public AlreadyParticipationException() {
        super("이미 참가한 대회입니다.");
    }
}

package org.antop.billiardslove.exception;

public class CantParticipationException extends Exception {
    public CantParticipationException() {
        super("참가할 수 없는 대회입니다.");
    }
}

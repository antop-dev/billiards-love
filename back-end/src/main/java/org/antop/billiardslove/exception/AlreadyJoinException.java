package org.antop.billiardslove.exception;

/**
 * 이미 참가한 대회일 때 예외
 *
 * @author jammini
 */
public class AlreadyJoinException extends BadRequestException {
    public AlreadyJoinException() {
        super("이미 참가한 대회입니다.");
    }
}

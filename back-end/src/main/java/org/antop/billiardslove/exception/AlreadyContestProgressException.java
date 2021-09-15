package org.antop.billiardslove.exception;

/**
 * 이미 진행된 대회일때 예외
 *
 * @author jammini
 */
public class AlreadyContestProgressException extends BadRequestException {
    public AlreadyContestProgressException() {
        super("이미 진행된 대회입니다.");
    }
}

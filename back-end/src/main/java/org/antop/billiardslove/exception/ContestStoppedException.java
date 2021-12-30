package org.antop.billiardslove.exception;

/**
 * 중지된 대회일 때 예외
 *
 * @author jammini
 */
public class ContestStoppedException extends BadRequestException {
    public ContestStoppedException() {
        super("중지된 대회입니다.");
    }
}

package org.antop.billiardslove.exception;

/**
 * 대회를 찾을 수 없을 때 예외
 *
 * @author antop
 */
public class ContestNotFoundException extends RuntimeException {
    public ContestNotFoundException() {
        super("대회를 찾을 수 없습니다.");
    }
}

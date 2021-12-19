package org.antop.billiardslove.exception;

/**
 * 이미 확정된 경기일 경우 예외
 *
 * @author antop
 */
public class MatchClosedException extends RuntimeException {
    public MatchClosedException() {
        super("이미 확정된 경기입니다.");
    }
}

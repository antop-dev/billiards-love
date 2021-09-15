package org.antop.billiardslove.exception;

/**
 * 경기를 찾을 수 없을 때 예외
 *
 * @author antop
 */
public class MatchNotFoundException extends NotFoundException {
    public MatchNotFoundException() {
        super("경기를 찾을 수 없습니다.");
    }
}

package org.antop.billiardslove.exception;

/**
 * 회원이 참여하지 않은 경기에 접근한 경우
 *
 * @author antop
 */
public class NotJoinedMatchException extends BadRequestException {
    public NotJoinedMatchException() {
        super("참여하지 않은 경기입니다.");
    }
}

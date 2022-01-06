package org.antop.billiardslove.exception;

/**
 * 이미 최대 참가자만큼 창여했을 경우
 *
 * @author antop
 */
public class ContestMaxJoinerException extends BadRequestException {
    public ContestMaxJoinerException(int maxJoiner) {
        super("이미 최대 참가자 수인 " + maxJoiner + "명이 참가했습니다.");
    }
}

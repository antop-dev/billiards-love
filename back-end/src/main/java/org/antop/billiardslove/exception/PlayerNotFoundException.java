package org.antop.billiardslove.exception;

/**
 * 참가자를 찾을 수 없을 때 예외
 *
 * @author antop
 */
public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
        super("참가자를 찾을 수 없습니다.");
    }
}

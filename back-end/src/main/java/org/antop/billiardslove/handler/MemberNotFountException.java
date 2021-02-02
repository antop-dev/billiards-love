package org.antop.billiardslove.handler;

public class MemberNotFountException extends RuntimeException {
    public MemberNotFountException(long id) {
        super("" + id);
    }
}

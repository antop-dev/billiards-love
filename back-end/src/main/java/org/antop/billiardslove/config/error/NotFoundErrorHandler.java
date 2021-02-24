package org.antop.billiardslove.config.error;

import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.exception.PlayerNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 404 Not Found API 응답을 주게 되는 응답 처리
 *
 * @author antop
 */
@ControllerAdvice
@RestController
public class NotFoundErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            MemberNotFountException.class,
            ContestNotFoundException.class,
            PlayerNotFountException.class
    })
    public ErrorMessage notFound(Exception e) {
        return ErrorMessage.notFound(e.getMessage());
    }

}

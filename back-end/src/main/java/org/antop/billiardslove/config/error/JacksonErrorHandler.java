package org.antop.billiardslove.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * JSON 변환 단계에서 발생하는 예외를 HTTP 응답으로 처리
 *
 * @author antop
 */
@ControllerAdvice
@RestController
public class JacksonErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorMessage processValidationError(HttpMessageNotReadableException e) {
        String message = Optional.ofNullable(e.getMessage())
                .map(s -> s.split("; ")[0])
                .orElse(null);
        return ErrorMessage.badRequest(message);
    }

}

package org.antop.billiardslove.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link javax.validation.Valid} 단계에서 발생한 에러 응답 처리
 *
 * @author antop
 */
@ControllerAdvice
@RestController
public class ValidErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage processValidationError(MethodArgumentNotValidException e) {
        return bindingResult(e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorMessage processBindException(BindException e) {
        return bindingResult(e.getBindingResult());
    }

    private ErrorMessage bindingResult(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField());
            sb.append(" is ");
            sb.append(fieldError.getDefaultMessage());
            sb.append(" (input : ");
            sb.append(fieldError.getRejectedValue());
            sb.append(")");
        }
        return ErrorMessage.badRequest(sb.toString());
    }

}

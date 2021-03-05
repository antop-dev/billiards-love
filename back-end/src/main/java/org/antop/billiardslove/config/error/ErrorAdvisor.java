package org.antop.billiardslove.config.error;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.CantParticipateContestStateException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * 웹 예외 핸들러<br>
 * 시스템상에 발생하는 예외를 잡아서 공통으로 처리한다.
 *
 * @author antop
 */
@Slf4j
@RestControllerAdvice
public class ErrorAdvisor {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ContestNotFoundException.class,
            MemberNotFountException.class,
            PlayerNotFoundException.class
    })
    ErrorMessage notFound(Exception e) {
        return ErrorMessage.notFound(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            AlreadyParticipationException.class,
            CantParticipateContestStateException.class
    })
    ErrorMessage badRequest(Exception e) {
        return ErrorMessage.badRequest(e.getMessage());
    }

    /**
     * JSON 변환(Jackson) 단계에서 발생하는 예외 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorMessage processValidationError(HttpMessageNotReadableException e) {
        String message = Optional.ofNullable(e.getMessage())
                .map(s -> s.split("; ")[0])
                .orElse(null);
        return ErrorMessage.badRequest(message);
    }

    /**
     * {@link javax.validation.Valid} 검사 예외
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage processValidationError(MethodArgumentNotValidException e) {
        return bindingResult(e.getBindingResult());
    }

    /**
     * {@link javax.validation.Valid} 검사 예외
     */
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

    /**
     * 권한 없음 에러 처리
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    ErrorMessage forbidden(Exception e) {
        return ErrorMessage.forbidden("권한이 없습니다.");
    }

    /**
     * 그 외 모든 에러 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorMessage internalServerError(Exception e) {
        log.debug("catch error!", e);
        return ErrorMessage.internalServerError(e.getMessage());
    }

}

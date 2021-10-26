package org.antop.billiardslove.config.error;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.exception.BadRequestException;
import org.antop.billiardslove.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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

    /**
     * 404 Not Found 예외 처리
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ErrorMessage notFound(Exception e) {
        return ErrorMessage.notFound(e.getMessage());
    }

    /**
     * 400 Bad Request 예외 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    ErrorMessage badRequest(Exception e) {
        return ErrorMessage.badRequest(e.getMessage());
    }

    /**
     * JSON 변환(Jackson) 단계에서 발생하는 예외 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorMessage processValidationError(HttpMessageNotReadableException e) {
        log.debug("fail convert json → object. exception class = {}, message = {}", e.getClass(), e.getMessage());
        return ErrorMessage.badRequest("잘못된 포멧입니다.");
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
        return Optional.ofNullable(bindingResult.getFieldError())
                .map(it -> ErrorMessage.badRequest(it.getDefaultMessage()))
                .orElse(ErrorMessage.badRequest("잘못된 요청입니다."));
    }

    /**
     * 권한 없음 에러 처리
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    ErrorMessage forbidden() {
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

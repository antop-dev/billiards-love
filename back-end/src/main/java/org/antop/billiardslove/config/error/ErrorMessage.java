package org.antop.billiardslove.config.error;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpStatus;

/**
 * API 응답 에러 메세지
 *
 * @author antop
 */
@Getter
@FieldNameConstants
public class ErrorMessage {
    /**
     * 에러 코드 (HTTP STATUS CODE)
     */
    private final int code;
    /**
     * 에러 메세지
     */
    private final String message;

    private ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorMessage of(HttpStatus status, String message) {
        return new ErrorMessage(status.value(), message);
    }

    public static ErrorMessage badRequest(String message) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static ErrorMessage notFound(String message) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), message);
    }

    public static ErrorMessage internalServerError(String message) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public static ErrorMessage forbidden(String message) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), message);
    }

}

package sample.app.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BusinessException {
    private static final String ERROR_CODE = "INVALID_REQUEST";

    public InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ERROR_CODE);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST, ERROR_CODE);
    }

}

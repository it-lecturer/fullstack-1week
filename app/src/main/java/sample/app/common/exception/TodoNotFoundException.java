package sample.app.common.exception;

import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends BusinessException {
    private static final String ERROR_CODE = "TODO_NOT_FOUND";

    public TodoNotFoundException(String todoId) {
        super("Todo not found with id: " + todoId, HttpStatus.NOT_FOUND, ERROR_CODE);
    }

    public TodoNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND, ERROR_CODE);
    }

}

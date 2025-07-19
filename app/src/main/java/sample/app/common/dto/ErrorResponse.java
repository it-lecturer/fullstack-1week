
package sample.app.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import sample.app.common.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp;
    private final String path;
    private final List<ValidationError> validationErrors;

    @Getter
    @Builder
    public static class ValidationError {
        private final String field;
        private final Object rejectedValue;
        private final String message;
    }

    // 일반 에러 응답 생성
    public static ErrorResponse of(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    // 비즈니스 예외 응답 생성
    public static ErrorResponse of(BusinessException ex, String path) {
        return ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    // 검증 에러 응답 생성
    public static ErrorResponse of(String errorCode, String message, String path, List<ValidationError> validationErrors) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .validationErrors(validationErrors)
                .build();
    }
}
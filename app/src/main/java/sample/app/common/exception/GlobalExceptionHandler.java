package sample.app.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import sample.app.common.dto.ErrorResponse;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
        log.warn("Business exception occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(ex, request.getRequestURI());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /**
     * @Valid 검증 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation failed: {}", ex.getMessage());

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.ValidationError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.of(
                "VALIDATION_FAILED",
                "입력 데이터 검증에 실패했습니다.",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * @ModelAttribute 바인딩 실패 예외 처리
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBindException(
            BindException ex, HttpServletRequest request) {
        log.warn("Binding failed: {}", ex.getMessage());

        // BindException은 BindingResult를 상속받으므로 직접 메서드 호출 가능
        List<ErrorResponse.ValidationError> validationErrors = ex.getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.ValidationError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());


        ErrorResponse errorResponse = ErrorResponse.of(
                "BINDING_FAILED",
                "요청 데이터 바인딩에 실패했습니다.",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * @ValidUUID 검증 실패 예외 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("Constraint violation: {}", ex.getMessage());

        List<ErrorResponse.ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponse.ValidationError.builder()
                        .field(violation.getPropertyPath().toString())
                        .rejectedValue(violation.getInvalidValue())
                        .message(violation.getMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.of(
                "CONSTRAINT_VIOLATION",
                "입력 데이터 검증에 실패했습니다.",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    /**
     * 필수 요청 파라미터 누락 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing request parameter: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "MISSING_PARAMETER",
                String.format("필수 요청 파라미터가 누락되었습니다: %s", ex.getParameterName()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 메서드 파라미터 타입 불일치 예외 처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Method argument type mismatch: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "TYPE_MISMATCH",
                String.format("파라미터 타입이 올바르지 않습니다: %s", ex.getName()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * HTTP 메서드 지원하지 않음 예외 처리
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("Method not allowed: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "METHOD_NOT_ALLOWED",
                String.format("지원하지 않는 HTTP 메서드입니다: %s", ex.getMethod()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * 핸들러를 찾을 수 없음 예외 처리 (404)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest request) {
        log.warn("No handler found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "NOT_FOUND",
                "요청한 리소스를 찾을 수 없습니다.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * JSON 파싱 실패 예외 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Message not readable: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "MESSAGE_NOT_READABLE",
                "요청 본문을 읽을 수 없습니다. JSON 형식을 확인해주세요.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * IllegalArgumentException 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "INVALID_ARGUMENT",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 그 외 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(
        Exception ex, HttpServletRequest request) {

        String uri = request.getRequestURI();

        // Swagger 요청은 무시하여 Spring이 직접 처리하도록
        if (isSwaggerRequest(uri)) {
                return null;
        }

        log.error("Unhandled exception occurred", ex);

        ErrorResponse errorResponse = ErrorResponse.of(
                "INTERNAL_SERVER_ERROR",
                "서버 내부 오류가 발생했습니다.",
                uri
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }


    /**
     * Swagger 관련 요청인지 확인
     */
    private boolean isSwaggerRequest(String uri) {
        if (uri == null) return false;

        return uri.startsWith("/swagger-ui") ||
                uri.startsWith("/swagger-ui.html") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-resources") ||
                uri.startsWith("/webjars");
    }
}

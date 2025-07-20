package sample.app.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
    String message() default "올바르지 않은 UUID 형식입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
package sample.app.common.annotation;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import sample.app.common.generator.UUIDv7Generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(UUIDv7Generator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDv7 {
}

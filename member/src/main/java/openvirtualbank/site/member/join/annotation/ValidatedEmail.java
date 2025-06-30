package openvirtualbank.site.member.join.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import openvirtualbank.site.member.join.global.validator.StrictEmailValidator;

@Constraint(validatedBy = StrictEmailValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedEmail {
	String message() default "유효하지 않은 이메일 형식입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

package openvirtualbank.site.member.join.global.validator;

import org.apache.commons.validator.routines.EmailValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import openvirtualbank.site.member.join.annotation.ValidatedEmail;

public class StrictEmailValidator implements ConstraintValidator<ValidatedEmail, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return false;

		// Apache Commons Validator로 이메일 형식 검사
		return EmailValidator.getInstance(true).isValid(value);
	}
}

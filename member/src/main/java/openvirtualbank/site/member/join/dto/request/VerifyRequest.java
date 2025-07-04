package openvirtualbank.site.member.join.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import openvirtualbank.site.member.join.annotation.ValidatedEmail;

public record VerifyRequest(
	@NotBlank
	String key,
	@Min(value = 100000, message = "인증번호는 6자리여야 합니다.")
	@Max(value = 999999, message = "인증번호는 6자리여야 합니다.")
	int AuthNumber,
	@NotBlank
	@ValidatedEmail
	String email) {
}

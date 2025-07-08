package openvirtualbank.site.member.join.dto.request;

import jakarta.validation.constraints.NotBlank;
import openvirtualbank.site.member.join.annotation.ValidatedEmail;

public record EmailRequest(
	@NotBlank
	@ValidatedEmail
	String email) {
}

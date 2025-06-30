package openvirtualbank.site.member.join.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
	@NotBlank
	String email) {
}

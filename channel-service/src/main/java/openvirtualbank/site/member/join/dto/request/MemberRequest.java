package openvirtualbank.site.member.join.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.NonNull;
import openvirtualbank.site.member.enums.MemberRole;
import openvirtualbank.site.member.join.annotation.ValidatedEmail;

public record MemberRequest(
	@NotBlank
	@ValidatedEmail
	String email,
	@NotBlank
	String password,
	@Past
	@NotNull
	LocalDate Birthday,
	@NotBlank
	String first_name,
    @NotNull
	MemberRole role
) {
}

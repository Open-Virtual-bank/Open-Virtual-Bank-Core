package openvirtualbank.site.member.join.dto.response;

import java.time.LocalDate;

import openvirtualbank.site.member.enums.MemberRole;

public record MemberResponse(
	Long id,
	String email,
	String password,
	LocalDate Birthday,
	String first_name,
	MemberRole role
) {
}

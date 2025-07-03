package openvirtualbank.site.member.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import openvirtualbank.site.member.entity.Member;
import openvirtualbank.site.member.join.dto.request.MemberRequest;
import openvirtualbank.site.member.join.dto.response.MemberResponse;

@Component
@RequiredArgsConstructor
public class MemberConverter {

	private final PasswordEncoder passwordEncoder;

	public Member toEntity(MemberRequest request){
		return Member.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.birthDate(request.Birthday())
			.firstName(request.first_name())
			.birthDate(request.Birthday())
			.memberRole(request.role())
			.build();
	}

	public MemberResponse toResponse(Member member){
		return new MemberResponse(member.getId(),
									member.getEmail(),
									member.getPassword(),
									member.getBirthDate(),
									member.getFirstName(),
									member.getMemberRole());
	}
}

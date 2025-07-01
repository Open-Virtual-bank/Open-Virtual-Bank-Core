package openvirtualbank.site.member.join.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import openvirtualbank.site.domain.global.error.ErrorCode;
import openvirtualbank.site.domain.global.exception.MemberException;
import openvirtualbank.site.member.converter.MemberConverter;
import openvirtualbank.site.member.entity.Member;
import openvirtualbank.site.member.join.dto.request.MemberRequest;
import openvirtualbank.site.member.join.dto.response.MemberResponse;
import openvirtualbank.site.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberConverter memberConverter;


	@Transactional
	public MemberResponse joinMember(MemberRequest request){
		//해당 이메일로 가입된 Member가 있다면 예외 처리
		if (memberRepository.findByEmail(request.email()) != null){
			throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
		}
		Member newMember = memberConverter.toEntity(request);

		Member joinedMember = memberRepository.save(newMember);
		return memberConverter.toResponse(joinedMember);
	}
}

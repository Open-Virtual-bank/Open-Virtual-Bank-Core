package openvirtualbank.site.member.join.test;

import static org.assertj.core.api.Fail.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import openvirtualbank.site.member.entity.Member;
import openvirtualbank.site.member.enums.MemberRole;
import openvirtualbank.site.member.exception.MemberException;
import openvirtualbank.site.member.join.dto.request.MemberRequest;
import openvirtualbank.site.member.join.dto.response.MemberResponse;
import openvirtualbank.site.member.join.service.MemberService;
import openvirtualbank.site.member.repository.MemberRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JoinMemberTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void JoinMemberTest() {
		String date = "20000701";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		MemberRequest member = new MemberRequest("test@exmaple.com", "123456", LocalDate.parse(date, formatter), "김민수",
			MemberRole.MEMBER);
		MemberResponse response = memberService.joinMember(member);
		Member joinedMember = memberRepository.findByEmail(response.email());
		Assertions.assertThat(member.email()).isEqualTo(joinedMember.getEmail());
	}

	@Test
	void DuplicateEmailTest() {
		String date = "20000701";
		String date2 = "20010515";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		MemberRequest member1 = new MemberRequest("dummy@exmaple.com", "1234", LocalDate.parse(date, formatter), "김철수",
			MemberRole.MEMBER);
		MemberRequest member2 = new MemberRequest("dummy@exmaple.com", "5678", LocalDate.parse(date2, formatter), "김진수",
			MemberRole.MEMBER);

		memberService.joinMember(member1);

		try {
			memberService.joinMember(member2);
			fail();
		} catch (MemberException ex) {
			Assertions.assertThat(ex.getErrorMessage()).isEqualTo("이미 사용 중인 이메일입니다.");
		}
	}
}

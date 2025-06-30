package openvirtualbank.site.member.join.service;

import static openvirtualbank.site.domain.global.error.ErrorCode.*;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import openvirtualbank.site.member.join.dto.response.AuthNumberResponse;
import openvirtualbank.site.domain.global.exception.MemberException;
import openvirtualbank.site.member.join.dto.response.VerifyResponse;
import openvirtualbank.site.member.join.generator.RandomGenerator;
import openvirtualbank.site.member.join.generator.SaltGenerator;
import openvirtualbank.site.member.join.util.RedisUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthMailService {

	private static final Long EXPIRATION = 1800000L; // 30분
	private final RedisUtil redisUtil;
	private final RandomGenerator randomGenerator;
	private final SaltGenerator saltGenerator;
	private final SendMailService sendMailService;
	private final RateLimitService rateLimitService;

	@Transactional
	public AuthNumberResponse sendCodeEmail(String email) throws NoSuchAlgorithmException {
		int authNumber = randomGenerator.makeRandomNumber();
		String title = "회원 가입 인증 이메일입니다.";
		String content = "인증 번호는 " + authNumber + "입니다.";

		String key = UUID.randomUUID().toString();
		String salt = saltGenerator.generateSalt();
		// 30분 이내에 10번 미만으로 인증번호 전송 시
		if (rateLimitService.checkAPICall(email)) {
			sendMailService.sendEmail(email, title, content);
		}else{
			throw new MemberException(TOO_MANY_REQUESTS);
		}
		// 레디스에 인증번호 저장
		redisUtil.saveAuthNumber(key, salt, String.valueOf(authNumber), EXPIRATION);
		// 레디스에 salt 저장
		redisUtil.saveSalt(key, salt, EXPIRATION);
		return new AuthNumberResponse(key, authNumber);
	}

	@Transactional(readOnly = true)
	public VerifyResponse verifyCode(String uuid, int AuthNumber) throws Exception{
		String findCode = (String) redisUtil.findEmailAuthNumberByKey(uuid);
		boolean status = String.valueOf(AuthNumber).equals(findCode);
		return new VerifyResponse(status);
	}
}

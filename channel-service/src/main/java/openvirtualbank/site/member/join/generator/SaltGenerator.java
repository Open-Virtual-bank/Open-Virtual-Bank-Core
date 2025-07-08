package openvirtualbank.site.member.join.generator;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SaltGenerator {
	public String generateSalt() {
		//16바이트는 보안적으로 충분히 안전한 길이
		byte[] saltBytes = new byte[16];
		new SecureRandom().nextBytes(saltBytes);  // 보안 랜덤
		return Base64.getUrlEncoder().withoutPadding().encodeToString(saltBytes);  // URL-safe base64
	}
}

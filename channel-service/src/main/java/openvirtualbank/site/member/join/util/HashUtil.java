package openvirtualbank.site.member.join.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import openvirtualbank.site.domain.global.error.ErrorCode;
import openvirtualbank.site.domain.global.exception.BusinessException;

public class HashUtil {
	public static String sha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			// 바이트 배열 → 16진수 문자열로 변환
			StringBuilder hexString = new StringBuilder();
			for (byte b : encodedHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException(ErrorCode.HASHING_FAILURE.getHttpStatus(), ErrorCode.HASHING_FAILURE.getCode(),
				ErrorCode.HASHING_FAILURE.getMessage());
		}

	}
}

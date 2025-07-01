package openvirtualbank.site.member.join.util;

import static openvirtualbank.site.member.join.util.HashUtil.*;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, String> redisTemplate;
	private static final String AUTH_NUMBER_PREFIX = "AuthNumber:";
	private static final String SALT_PREFIX = "Salt:";
	private static final String EMAIL_API_CALL_COUNT = "EmailAPICallCount:";

	public void saveAuthNumber(String key, String salt, String emailAuthNumber, Long expiration) throws
		NoSuchAlgorithmException {

		String authNumberKey = generateAuthNumberKey(key, salt);
		redisTemplate.opsForValue().set(authNumberKey, emailAuthNumber, expiration, TimeUnit.MILLISECONDS);
	}

	public void saveSalt(String key, String salt, Long expiration) {
		redisTemplate.opsForValue().set(SALT_PREFIX + key, salt, expiration, TimeUnit.MILLISECONDS);
	}

	public String generateAuthNumberKey(String key, String salt) throws NoSuchAlgorithmException {
		String combinedKey = key + salt;
		String hashedKey = sha256(combinedKey);
		return AUTH_NUMBER_PREFIX + hashedKey;
	}

	public String findAPICallByKey(String email) throws NoSuchAlgorithmException {
		String hashedEmail = sha256(email);
		return redisTemplate.opsForValue().get(EMAIL_API_CALL_COUNT + hashedEmail);
	}

	public void saveAPICall(String email, String number, Long expiration) throws NoSuchAlgorithmException {
		String hashedEmail = sha256(email);
		redisTemplate.opsForValue().set(EMAIL_API_CALL_COUNT + hashedEmail, number, expiration, TimeUnit.MILLISECONDS);
	}

	public void updateAPICall(String email, String s, Long expiration) throws NoSuchAlgorithmException {
		String hashedEmail = sha256(email);
		redisTemplate.opsForValue().set(EMAIL_API_CALL_COUNT + hashedEmail, s, expiration, TimeUnit.MILLISECONDS);
	}

	public Object findEmailAuthNumberByKey(String key) throws NoSuchAlgorithmException {
		String salt = redisTemplate.opsForValue().get(SALT_PREFIX + key);
		String authNumberKey = generateAuthNumberKey(key, salt);
		return redisTemplate.opsForValue().get(authNumberKey);
	}

	public void deleteKey(String key, String email) throws NoSuchAlgorithmException {
		String salt = redisTemplate.opsForValue().get(SALT_PREFIX + key);
		String combinedKey = generateAuthNumberKey(key, salt);
		String hashedEmail = sha256(email);
		redisTemplate.delete(combinedKey);
		redisTemplate.delete(SALT_PREFIX + key);
		redisTemplate.delete(EMAIL_API_CALL_COUNT + hashedEmail);

	}
}

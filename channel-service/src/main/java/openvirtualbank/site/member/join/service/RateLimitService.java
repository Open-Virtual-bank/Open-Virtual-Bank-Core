package openvirtualbank.site.member.join.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import openvirtualbank.site.member.join.util.RedisUtil;

@Service
@RequiredArgsConstructor
public class RateLimitService {
	private static final int MAX_API_CALL = 10;
	private static final Long EXPIRATION = 1800000L;
	private final RedisUtil redisUtil;

	public boolean checkAPICall(String email) {
		String apiCall = redisUtil.findAPICallByKey(email);
		if (apiCall == null) {
			redisUtil.saveAPICall(email, "1", EXPIRATION);
			return true;
		} else if (Integer.parseInt(apiCall) < MAX_API_CALL) {
			redisUtil.updateAPICall(email, String.valueOf(Integer.parseInt(apiCall) + 1), EXPIRATION);
			return true;
		}
		return false;
	}

}

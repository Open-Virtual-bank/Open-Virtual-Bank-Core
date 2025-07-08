package openvirtualbank.site.member.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode {
	TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "MB-02", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요"),
	DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "MB-03", "이미 사용 중인 이메일입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}

package openvirtualbank.site.domain.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	NOT_VALID(HttpStatus.BAD_REQUEST, "GLB-01", "유효성 검사에 실패했습니다"),
	REQUEST_BODY_NOT_VALID(HttpStatus.BAD_REQUEST, "GLB-02", "요청 본문이 유효하지 않습니다"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "GLB-03", "요청한 리소스를 찾을 수 없습니다"),
	MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "GLB-04", "요청 파라미터가 누락되었습니다"),
	HASHING_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "MB-01", "서버에서 암호화 처리 중 오류가 발생했습니다");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
	}

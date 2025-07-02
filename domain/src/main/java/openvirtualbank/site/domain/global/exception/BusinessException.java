package openvirtualbank.site.domain.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {
	private final HttpStatus status;
	private final String errorCode;
	private final String errorMessage;

	public BusinessException(HttpStatus status, String errorCode, String errorMessage) {
		super(errorMessage);
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}

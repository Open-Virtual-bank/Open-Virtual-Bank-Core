package openvirtualbank.site.domain.global.exception;

import openvirtualbank.site.domain.global.error.ErrorCode;

public class MemberException extends BusinessException {
	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}

}

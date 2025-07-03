package openvirtualbank.site.member.exception;

import openvirtualbank.site.domain.global.exception.BusinessException;

public class MemberException extends BusinessException {
	public MemberException(MemberErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
	}

}

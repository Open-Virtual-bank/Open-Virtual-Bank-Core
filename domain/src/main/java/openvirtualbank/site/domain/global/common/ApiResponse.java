package openvirtualbank.site.domain.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import openvirtualbank.site.domain.global.error.ErrorResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	//성공, 실패 여부
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private ApiStatus status;
	private T data;
	private ErrorResponse error;

	public static <T> ApiResponse<T> success(T data) {
		ApiResponse<T> response = new ApiResponse<>();
		response.status = ApiStatus.SUCCESS;
		response.data = data;
		return response;
	}

	public static <T> ApiResponse<T> failure(ErrorResponse error) {
		ApiResponse<T> response = new ApiResponse<>();
		response.status = ApiStatus.FAILURE;
		response.error = error;
		return response;
	}
}

package openvirtualbank.site.orchestration.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import openvirtualbank.site.domain.global.error.ErrorCode;
import openvirtualbank.site.domain.global.error.ErrorResponse;
import openvirtualbank.site.domain.global.error.ErrorResponse.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //유효성 검사 실패
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        List<FieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldErrorResponse::of).toList();
        List<String> errors = fieldErrors.stream().map(FieldErrorResponse::getField).toList();

        log.error("[MethodArgumentNotValidException] : {}", errors);
        log.error("[MethodArgumentNotValidException] 발생 지점 : {} | {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_VALID, httpRequest, fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //http 요청으로 온 RequestBody 누락
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[HttpMessageNotReadableException] : {}", ex.getMessage());
        log.error("[HttpMessageNotReadableException] 발생 지점 : {} | {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.REQUEST_BODY_NOT_VALID, httpRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    //존재하지 않는 url 요청
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[NoResourceFoundException] : {}", ex.getMessage());
        log.error("[NoResourceFoundException] 발생 지점 : {} | {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, httpRequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //http 요청으로 온 RequestParameter 누락
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        log.error("[MissingServletRequestParameterException] : {}", ex.getMessage());
        log.error("[MissingServletRequestParameterException] 발생 지점 : {} | {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER, httpRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

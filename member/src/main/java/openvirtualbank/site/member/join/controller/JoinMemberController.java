package openvirtualbank.site.member.join.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import openvirtualbank.site.domain.global.common.ApiResponse;
import openvirtualbank.site.member.join.dto.request.EmailRequest;
import openvirtualbank.site.member.join.dto.request.VerifyRequest;
import openvirtualbank.site.member.join.dto.response.AuthNumberResponse;
import openvirtualbank.site.member.join.dto.response.VerifyResponse;
import openvirtualbank.site.member.join.service.AuthMailService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JoinMemberController {

	private final AuthMailService authMailService;

	@PostMapping("/email/send-code")
	public ResponseEntity<ApiResponse<AuthNumberResponse>> sendEmail(@RequestBody @Valid EmailRequest request) throws
		NoSuchAlgorithmException {
		AuthNumberResponse response = authMailService.sendCodeEmail(request.email());
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
	}

	@GetMapping("/email/verify-code")
	public ResponseEntity<ApiResponse<VerifyResponse>> verifyEmail(@RequestBody @Valid VerifyRequest request) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(authMailService.verifyCode(request.uuid(), request.AuthNumber())));
	}


}

package openvirtualbank.site.member.join.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
	public ResponseEntity<AuthNumberResponse> sendEmail(@RequestBody @Valid EmailRequest request) throws
		NoSuchAlgorithmException {
		AuthNumberResponse response = authMailService.sendCodeEmail(request.email());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/email/verify-code")
	public ResponseEntity<VerifyResponse> verifyEmail(@RequestBody @Valid VerifyRequest request) throws Exception{
		return ResponseEntity.ok(authMailService.verifyCode(request.uuid(), request.AuthNumber()));
	}


}

package openvirtualbank.site.member.join.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import openvirtualbank.site.member.join.dto.request.EmailRequest;
import openvirtualbank.site.member.join.dto.response.AuthNumberResponse;
import openvirtualbank.site.member.join.service.AuthMailService;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final AuthMailService authMailService;

	@PostMapping("/auth/email/send-code")
	public ResponseEntity<AuthNumberResponse> sendEmail(@RequestBody @Valid EmailRequest request) throws
		NoSuchAlgorithmException {
		AuthNumberResponse response = authMailService.sendCodeEmail(request.email());
		return ResponseEntity.ok(response);
	}
}

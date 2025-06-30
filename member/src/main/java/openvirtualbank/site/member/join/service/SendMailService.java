package openvirtualbank.site.member.join.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendMailService {
	private final JavaMailSender mailSender;

	@Value("${spring.email.username}")
	private String username;

	public void sendEmail(String to, String subject, String content) {
		MimeMessagePreparator messagePreparator =
			mimeMessage -> {
				//true는 멀티파트 메세지를 사용하겠다는 의미
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(username);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content, true);
			};
		mailSender.send(messagePreparator);
	}
}

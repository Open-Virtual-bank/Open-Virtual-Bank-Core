package openvirtualbank.site.member.join.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("${spring.email.host}")
	private String host;

	@Value("${spring.email.port}")
	private int port;

	@Value("${spring.email.username}")
	private String username;

	@Value("${spring.email.password}")
	private String password;

	@Value("${spring.email.properties.mail.smtp.auth}")
	private boolean auth;

	@Value("${spring.email.properties.mail.smtp.starttls.enable}")
	private boolean starttlsEnable;

	@Value("${spring.email.properties.mail.smtp.starttls.required}")
	private boolean starttlsRequired;

	@Value("${spring.email.properties.mail.smtp.connectiontimeout}")
	private int connectionTimeout;

	@Value("${spring.email.properties.mail.smtp.timeout}")
	private int timeout;

	@Value("${spring.email.properties.mail.smtp.writetimeout}")
	private int writeTimeout;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();

		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.starttls.required", starttlsRequired);
		props.put("mail.smtp.connectiontimeout", connectionTimeout);
		props.put("mail.smtp.timeout", timeout);
		props.put("mail.smtp.writetimeout", writeTimeout);
		return mailSender;
	}
}

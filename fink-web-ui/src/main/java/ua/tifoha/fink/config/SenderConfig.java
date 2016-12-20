package ua.tifoha.fink.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ua.tifoha.fink.services.sms.MessageSender;
import ua.tifoha.fink.services.sms.TurboSmsMessageSender;

@Configuration
@PropertySource ("classpath:password.properties")
@PropertySource ("classpath:application.properties")
public class SenderConfig {
	@Bean
	public JavaMailSender javaMailService(@Value ("${mail.username}") String username,
										  @Value ("${mail.password}") String password,
										  @Value ("${email.host:smtp.gmail.com}") String host,
										  @Value ("${email.port:587}") Integer port
	) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	@Bean
	public MessageSender turboSmsMassageSender(@Value ("${sms.turbosms.login}") String username,
											   @Value ("${sms.turbosms.password}") String password
	) {
		return new TurboSmsMessageSender(username, password);
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "true");
		return properties;
	}
}

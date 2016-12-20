package ua.tifoha.fink.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.tifoha.fink.services.sms.Message;
import ua.tifoha.fink.services.sms.MessageSender;
import ua.tifoha.fink.services.sms.SimpleMessage;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SenderConfig.class)
public class SenderConfigTest {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private MessageSender messageSender;

	@Test
	public void shouldSendMessage() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("tifoha@gmail.com");
		message.setTo("tifoha@gmail.com");
		message.setSubject("Test message");
		message.setText("This is test message");
		mailSender.send(message);

	}

	@Test
	public void shouldSendSMS() throws Exception {
		Message message = new SimpleMessage("+380971396134", "test message");
		messageSender.send(message);
	}
}
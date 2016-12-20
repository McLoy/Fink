package ua.tifoha.fink.services.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.turbosms.api.TurboSmsApiImpl;

//@Service
public class TurboSmsMessageSender implements MessageSender {
	@Value ("${sms.turbosms.sender:AlfaHolding}")
	private String sender;
	private final TurboSmsApiImpl turboSmsApi;

	public TurboSmsMessageSender(String username, String password) {
		turboSmsApi = new TurboSmsApiImpl(username, password);
	}

	@Override
	public void send(Message message) {
		String sender = message.getSender() == null ? this.sender : message.getSender();
		turboSmsApi.sendSMS(sender,
				message.getRecipient(),
				message.getText(),
				message.getWapPush());
	}
}

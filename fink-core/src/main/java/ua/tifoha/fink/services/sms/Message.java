package ua.tifoha.fink.services.sms;

public interface Message {
	String getSender();

	String getRecipient();

	String getText();

	String getWapPush();
}

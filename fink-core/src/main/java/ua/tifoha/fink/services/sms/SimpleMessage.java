package ua.tifoha.fink.services.sms;

public class SimpleMessage implements Message {
	private String sender;
	private String recipient;
	private String text;
	private String wapPush = "";

	public SimpleMessage() {
		this(null, null);
	}

	public SimpleMessage(String recipient, String text) {
		this.recipient = recipient;
		this.text = text;
	}

	@Override
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getWapPush() {
		return wapPush;
	}

	public void setWapPush(String wapPush) {
		this.wapPush = wapPush;
	}
}

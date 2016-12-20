package ua.tifoha.fink.quartz.job;

import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import ua.tifoha.fink.services.sms.Message;
import ua.tifoha.fink.services.sms.MessageSender;
import ua.tifoha.fink.services.sms.SimpleMessage;

public class SimplePingJob extends AbstractPingJob {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private MessageSender messageSender;

	@JobData
	private static ReportCause reportCause = ReportCause.FAIL;

	@JobData
	private String phoneNumber;

	@JobData
	private String emailAddress;

	public enum ReportCause {
		SUCCESS, FAIL;
	}

	public SimplePingJob() {
	}

	public SimplePingJob(String host, int port, int timeout, int attemptCount) {
		super(host, port, timeout, attemptCount);
	}

	public static ReportCause getReportCause() {
		return reportCause;
	}

	public static void setReportCause(ReportCause reportCause) {
		SimplePingJob.reportCause = reportCause;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	protected void onSuccess() {
		if (reportCause == ReportCause.SUCCESS) {
			sendReport(ReportCause.SUCCESS);
		}
	}

	@Override
	protected void onFail() {
		if (reportCause == ReportCause.FAIL) {
			sendReport(ReportCause.FAIL);
		}
	}

	private void sendReport(ReportCause cause) {
		if (StringUtils.isNotBlank(phoneNumber)) {
			Message message = new SimpleMessage(phoneNumber, getMessageText(cause));
			messageSender.send(message);
		}
		if (StringUtils.isNotBlank(emailAddress)) {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject("Ping " + cause);
			email.setText(getMessageText(cause));
			mailSender.send(email);
		}
	}

	private String getMessageText(ReportCause cause) {
		return format("%s%nServer: %s:%d%nAttempt: %d", cause, host, port, attemptCount);
	}

}

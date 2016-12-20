package ua.tifoha.fink.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tifoha.fink.quartz.job.JobData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class AbstractPingJob implements Job {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -1;

	@JobData
	protected String host;

	@JobData
	protected int port;

	@JobData
	protected int timeout;

	@JobData
	protected int attemptCount;
	protected Supplier<Socket> socketSupplier = Socket::new;

	public AbstractPingJob() {
	}

	public AbstractPingJob(String host, int port, int timeout, int attemptCount) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		this.attemptCount = attemptCount;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}

	void setSocketSupplier(Supplier<Socket> socketSupplier) {
		this.socketSupplier = socketSupplier;
	}

	protected void onSuccess() {
		System.out.println("OK - " + this);
	}

	protected void onFail() {
		System.out.println("ERROR - " + this);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[host=").append(host);
		sb.append(", port=").append(port);
		sb.append(", timeout=").append(timeout);
		sb.append(", attemptCount=").append(attemptCount);
		sb.append(']');
		return sb.toString();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		for (int i = 0; i < attemptCount; i++) {
			try (Socket socket = socketSupplier.get()) {
				socket.connect(new InetSocketAddress(host, port), timeout);
				onSuccess();
				return;
			} catch (IOException e) {
				onFail();
			}
		}
		System.out.println("PING FAIL - " + this);
	}
}

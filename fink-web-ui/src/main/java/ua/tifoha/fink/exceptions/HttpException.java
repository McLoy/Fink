package ua.tifoha.fink.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException{
	protected final HttpStatus status;

	public HttpException(HttpStatus status) {
		this.status = status;
	}

	public HttpException() {
		this(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public HttpStatus getStatus() {
		return status;
	}
}

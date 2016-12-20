package ua.tifoha.fink.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {
	public NotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}
}

package ua.tifoha.fink.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.tifoha.fink.exceptions.HttpException;
import ua.tifoha.fink.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	@ExceptionHandler(HttpException.class)
	public void handleControllerException(HttpException e, HttpServletResponse response) {
		response.setStatus(e.getStatus().value());
	}
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception ex) {

		return "404";//this is view name
	}
//	@ExceptionHandler (NotFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ModelAndView notFound() {
//		return new ModelAndView("404");
//	}
}

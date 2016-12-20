package ua.tifoha.fink.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.jws.WebResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping ("/")
public class MainController {
	@RequestMapping ("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@RequestMapping (value = "login", method = GET)
	public String notFound() {
		return "login";
	}
}

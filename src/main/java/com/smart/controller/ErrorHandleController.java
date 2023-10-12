package com.smart.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandleController implements ErrorController{

	@RequestMapping("/error")
	public String error()
	{
		return "error";
	}
}

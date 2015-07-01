package com.demo.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class ProductionHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
	
	@Override
	protected ModelAndView handleBindException(BindException ex, HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}

}

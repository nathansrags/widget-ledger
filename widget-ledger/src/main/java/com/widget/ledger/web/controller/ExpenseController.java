package com.widget.ledger.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExpenseController {

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "/createExpense" }, method = RequestMethod.GET)
	public ModelAndView onLoad(final HttpServletRequest request, final HttpServletResponse response, ModelMap model)
			throws IOException {
		return new ModelAndView("createExpense");
	}

}

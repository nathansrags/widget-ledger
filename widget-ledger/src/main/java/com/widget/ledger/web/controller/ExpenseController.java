package com.widget.ledger.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	@RequestMapping(value = "/createExpense", method = RequestMethod.POST)
	public ModelAndView onLoad(@ModelAttribute String expenseSheetName)
			throws IOException {
		return new ModelAndView("expense/createExpense");
	}

}

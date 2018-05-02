package com.widget.ledger.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExpenseController extends BaseController {

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/retriveSheet", method = RequestMethod.POST)
	public ModelAndView onLoad(@RequestParam("uniqueSheetId") String uniqueSheetId) throws IOException {
		return onLoadExpenses(uniqueSheetId);
	}

	@RequestMapping(value = "/createExpense", method = RequestMethod.POST)
	public ModelAndView onLoadExpense(@RequestParam("sheetName") String sheetName) throws IOException {
		return createNewSheet(sheetName);
	}

}

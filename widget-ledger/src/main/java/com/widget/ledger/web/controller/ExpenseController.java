package com.widget.ledger.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.widget.ledger.web.common.util.SecureIdGeneratorService;
import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.repository.LedgerExpensesRepository;
import com.widget.ledger.web.repository.LedgerSheetRepository;
import com.widget.ledger.web.repository.LedgerUserGroupsRepository;
import com.widget.ledger.web.service.ILedgerService;

@Controller
public class ExpenseController extends BaseController {

	@Autowired
	@Qualifier("ledgerServiceImpl")
	private ILedgerService ledgerService;

	@Autowired
	private LedgerSheetRepository sheetRepository;

	@Autowired
	private LedgerExpensesRepository expenseRepository;

	@Autowired
	private LedgerUserGroupsRepository userRepository;

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/createExpense", method = RequestMethod.POST)
	public ModelAndView onLoad(@RequestParam("sheetName") String sheetName) throws IOException {
		return onLoadExpenses(sheetName);
	}

	@RequestMapping(value = "/createExpense", method = RequestMethod.GET)
	public ModelAndView onLoadExpense(@RequestParam("sheetName") String sheetName) throws IOException {
		return onLoadExpenses(sheetName);
	}

	private void testSaveLedgerSheets() {
		LedgerSheets ledgerSheets = sheetRepository.findOne((long) 1);
		ledgerSheets.setSysActionCd("U");
		ledgerSheets.setLedgerSheetName("Test Sheet");
		ledgerSheets.setLedgerUniqueId(SecureIdGeneratorService.generateUID());
		sheetRepository.save(ledgerSheets);
	}

	private void testSaveLedgerExpense() {
		LedgerExpenses ledgerExpenses = new LedgerExpenses();
		ledgerExpenses.setLedgerSheet(sheetRepository.findOne((long) 1));
		ledgerExpenses.setPaidAmount(new BigDecimal(200.00));
		ledgerExpenses.setPaidBy("Senthil");
		ledgerExpenses.setPaidFor("Gopi");
		expenseRepository.save(ledgerExpenses);
	}

	private void testSaveUserGroup() {
		LedgerUserGroups user = new LedgerUserGroups();
		user.setUserName("Gopi");
		user.setLedgerSheet(sheetRepository.findOne((long) 1));
		user.setSysActionCd("I");
		user.setSysActionId("ADD_USER");
		user.setSysActionNm("ADD_USER");
		List<LedgerUserGroups> userList = new ArrayList<LedgerUserGroups>();
		userRepository.save(user);
	}

	private void testUpdateUserGroup() {
		LedgerSheets sheets = sheetRepository.findOne((long) 1);
		List<LedgerUserGroups> userList = sheets.getRelatedUsers();
		for (LedgerUserGroups ledgerUserGroups : userList) {
			ledgerUserGroups.setSysActionCd("U");
		}

	}

}

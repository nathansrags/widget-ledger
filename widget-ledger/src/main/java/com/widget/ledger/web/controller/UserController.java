package com.widget.ledger.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.service.ILedgerService;
import com.widget.ledger.web.service.IUserService;
import com.widget.ledger.web.to.DisplayTO;
import com.widget.ledger.web.to.ExpenseDetailsTO;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@RestController
public class UserController extends BaseController {

	@Autowired
	@Qualifier("ledgerUserGroupServiceImpl")
	private ILedgerService<LedgerUserGroups> ledgerUserGroupServiceImpl;

	@Autowired
	@Qualifier("userServiceImpl")
	private IUserService userServiceImpl;

	@Autowired
	@Qualifier("ledgerUserGroupTransformerImpl")
	private IGenericTransformer<UserDetailsTO, LedgerUserGroups> ledgerUserGroupTransformerImpl;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public DisplayTO saveUser(@RequestBody final UserDetailsTO userDetailsTO) throws IOException {
		LedgerUserGroups userGroup = ledgerUserGroupTransformerImpl.transformTOtoEntity(userDetailsTO);
		ledgerUserGroupServiceImpl.saveOrUpdate(userGroup);
		return fetchUserDetails(userDetailsTO);
	}

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
	public DisplayTO fetchUserDetails(@RequestBody final UserDetailsTO userDetailsTO) throws IOException {
		List<LedgerUserGroups> userGroups = ledgerUserGroupServiceImpl.findByCol(userDetailsTO.getUniqueSheetId());
		DisplayTO uiTO = new DisplayTO();
		uiTO.setUserDetailsTO(ledgerUserGroupTransformerImpl.transformEntityToTO(userGroups));
		return uiTO;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public ModelAndView deleteUser(@RequestBody final UserDetailsTO userDetailsTO) throws IOException {
		return onLoadExpenses("Unknown");
	}

	@Autowired
	@Qualifier("ledgerExpenseTranformerImpl")
	private IGenericTransformer<ExpenseDetailsTO, LedgerExpenses> ledgerExpenseTransformerImpl;

	@Autowired
	@Qualifier("ledgerExpenseServiceImpl")
	private ILedgerService<LedgerExpenses> ledgerExpenseServiceImpl;

	/**
	 * @param expenseDetailsTO
	 * @param uniqueSheetId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addExpense", method = RequestMethod.POST)
	public DisplayTO saveExpense(@RequestBody final ExpenseDetailsTO expenseDetailsTO) throws IOException {
		final LedgerExpenses ledgerExpense = ledgerExpenseTransformerImpl.transformTOtoEntity(expenseDetailsTO);
		buildLedgerExpense(ledgerExpense, expenseDetailsTO);
		ledgerExpenseServiceImpl.saveOrUpdate(ledgerExpense);
		return fetchExpenseDetails(expenseDetailsTO.getPaidBy().getUniqueSheetId());
	}

	private void buildLedgerExpense(LedgerExpenses expense, final ExpenseDetailsTO expenseDetailsTO) {
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl
				.findOne(expenseDetailsTO.getPaidBy().getUniqueSheetId());
		expense.setExpenseSheet(ledgerSheet);
		final Long paidById = expenseDetailsTO.getPaidBy().getUserDetailId();
		final List<Integer> paidForIds = new ArrayList<Integer>();
		for (final UserDetailsTO userDetails : expenseDetailsTO.getPaidFor()) {
			paidForIds.add(Integer.valueOf(userDetails.getUserDetailId().toString()));
		}
		//expense.setPaidBy(ledgerUserGroupServiceImpl.findById(paidById));
		//expense.setPaidFor(ledgerUserGroupServiceImpl.findByIds(paidForIds));
		ledgerExpenseServiceImpl.saveOrUpdate(expense);
	}

	/**
	 * @param uniqueSheetId
	 * @return DisplayTO
	 * @throws IOException
	 */
	@RequestMapping(value = "/getExpenseDetails", method = RequestMethod.POST)
	public DisplayTO fetchExpenseDetails(@RequestBody final String uniqueSheetId) throws IOException {
		final List<LedgerExpenses> ledgerExpenses = ledgerExpenseServiceImpl.findAll();
		final DisplayTO uiTO = new DisplayTO();
		uiTO.setExpenseDetailsTO(ledgerExpenseTransformerImpl.transformEntityToTO(ledgerExpenses));
		uiTO.setUniqueSheetId(uniqueSheetId);
		return uiTO;
	}

}

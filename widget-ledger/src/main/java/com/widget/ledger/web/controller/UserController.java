package com.widget.ledger.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.widget.ledger.web.exceptions.BusinessUncheckedException;
import com.widget.ledger.web.payment.service.PaymentCalculatorService;
import com.widget.ledger.web.service.ILedgerService;
import com.widget.ledger.web.service.IUserService;
import com.widget.ledger.web.to.DisplayTO;
import com.widget.ledger.web.to.ExpenseDetailsTO;
import com.widget.ledger.web.to.PaymentDetailsTO;
import com.widget.ledger.web.to.PaymentsTO;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@RestController
public class UserController extends BaseController {

	private static Logger LOG = LoggerFactory.getLogger(UserController.class);

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
	public DisplayTO addUser(@RequestBody final UserDetailsTO userDetailsTO) throws IOException {
		LedgerUserGroups userGroup = ledgerUserGroupTransformerImpl.transformTOtoEntity(userDetailsTO);
		if (ledgerUserGroupServiceImpl.find(userGroup) != null) {
			LOG.error("User already exits");
			throw new BusinessUncheckedException("User already exits");
		} else {
			ledgerUserGroupServiceImpl.saveOrUpdate(userGroup);
		}

		return getUserDetails(userDetailsTO);
	}

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
	public DisplayTO getUserDetails(@RequestBody final UserDetailsTO userDetailsTO) throws IOException {
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl.findOne(userDetailsTO.getUniqueSheetId());
		DisplayTO uiTO = new DisplayTO();
		uiTO.setUserDetailsTO(ledgerUserGroupTransformerImpl.transformEntityToTO(ledgerSheet.getRelatedUsers()));
		mapBreadCrums(ledgerSheet, uiTO);
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
	public DisplayTO addExpense(@RequestBody final ExpenseDetailsTO expenseDetailsTO) throws IOException {
		final LedgerExpenses ledgerExpense = ledgerExpenseTransformerImpl.transformTOtoEntity(expenseDetailsTO);
		saveLedgerExpense(ledgerExpense, expenseDetailsTO);
		return getExpenseDetails(expenseDetailsTO.getPaidBy().getUniqueSheetId());
	}

	private void saveLedgerExpense(LedgerExpenses expense, final ExpenseDetailsTO expenseDetailsTO) {
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl
				.findOne(expenseDetailsTO.getPaidBy().getUniqueSheetId());
		expense.setExpenseSheet(ledgerSheet);
		ledgerExpenseServiceImpl.saveOrUpdate(expense);
	}

	/**
	 * @param uniqueSheetId
	 * @return DisplayTO
	 * @throws IOException
	 */
	@RequestMapping(value = "/getExpenseDetails", method = RequestMethod.POST)
	public DisplayTO getExpenseDetails(@RequestBody final String uniqueSheetId) throws IOException {
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl.findOne(uniqueSheetId.replace("\"", ""));
		final DisplayTO uiTO = new DisplayTO();
		uiTO.setExpenseDetailsTO(ledgerExpenseTransformerImpl.transformEntityToTO(ledgerSheet.getRelatedExpenses()));
		uiTO.setUniqueSheetId(uniqueSheetId);
		mapBreadCrums(ledgerSheet, uiTO);
		return uiTO;
	}

	@RequestMapping(value = "/computeExpenses", method = RequestMethod.POST)
	public DisplayTO computeExpenses(@RequestBody final String uniqueSheetId) throws IOException {
		final Map<String, BigDecimal> nextMap = new HashMap<String, BigDecimal>();
		final List<PaymentDetailsTO> paymentsTO = new ArrayList<>();
		final LedgerSheets sheet = ledgerSheetServiceImpl.findOne(uniqueSheetId.replace("\"", ""));
		final List<LedgerExpenses> ledgerExpenses = sheet.getRelatedExpenses();
		PaymentCalculatorService service = new PaymentCalculatorService();
		if (ledgerExpenses.size() > 2) {
			try {
				nextMap.putAll(calculateIndividualBalances(ledgerExpenses));
			} catch (Exception e) {
				LOG.error("Error in Calculation Payment", e.getMessage());
				throw new BusinessUncheckedException("Error in calculating payment");
			}
			// service.calculatePayment(ledgerExpenses);
		}
		System.out.println("--- Final Settlement----------");
		final DisplayTO uiTO = new DisplayTO();
		final List<PaymentsTO> paymentList = new ArrayList<PaymentsTO>();
		for (final Object obj : nextMap.entrySet()) {
			Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
			System.out.println(entry.getKey() + " $ " + entry.getValue());
			final PaymentsTO paymentTO = new PaymentsTO();
			paymentTO.setPayAmt(entry.getValue().toEngineeringString());
			final String splitString[] = entry.getKey().split("pays");
			paymentTO.setPayFrom(splitString[0]);
			paymentTO.setPayTO(splitString[1]);
			paymentList.add(paymentTO);
		}
		uiTO.setPaymentTO(paymentList);
		uiTO.setPaymentDetailsTO(paymentsTO);
		return uiTO;
	}

	private Map<String, BigDecimal> calculateIndividualBalances(final List<LedgerExpenses> expenseList) {
		final Map<String, BigDecimal> userAmtMap = new HashMap<String, BigDecimal>();
		final Map<String, BigDecimal> nextMap = new HashMap<String, BigDecimal>();
		for (final LedgerExpenses expense : expenseList) {
			final BigDecimal amt = expense.getPaidAmount();
			BigDecimal splitAmt = amt.divide(new BigDecimal(expense.getPaidFor().size()), 2, RoundingMode.HALF_UP);
			for (LedgerUserGroups user : expense.getPaidFor()) {
				final String Key = user.getDisplayName();
				if (!userAmtMap.containsKey(Key)) {
					userAmtMap.put(Key, splitAmt);
				} else {
					userAmtMap.put(Key, (userAmtMap.get(Key).add(splitAmt)));
				}
			}
			// System.out.println(splitAmt);
		}
		System.out.println("--- Before Group----------");
		for (final Object obj : userAmtMap.entrySet()) {
			Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
			System.out.print("Key: " + entry.getKey());
			System.out.println(", Value: " + entry.getValue());
		}
		System.out.println("--- Grouped Data----------");
		List<LedgerExpenses> groupedList = groupAndSort(expenseList);
		for (LedgerExpenses expense : groupedList) {
			System.out.println(expense.getPaidBy().getDisplayName() + " " + expense.getPaidAmount());
		}
		System.out.println("--- After Group----------");
		final Map<String, BigDecimal> finalMap = new HashMap<String, BigDecimal>();
		for (final Object obj : userAmtMap.entrySet()) {
			Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
			BigDecimal totalSpend = getAmountFromGroupedList(groupedList, entry.getKey());
			finalMap.put(entry.getKey(), totalSpend.subtract(entry.getValue()));
			System.out.print("Key: " + entry.getKey());
			System.out.println(", Value: " + finalMap.get(entry.getKey()));
		}
		executeNextStep(finalMap, nextMap);
		System.out.println("--- Final Settlement----------");
		for (final Object obj : nextMap.entrySet()) {
			Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
			System.out.println(entry.getKey() + " $ " + entry.getValue());
		}
		return nextMap;

	}

	private void executeNextStep(final Map<String, BigDecimal> finalMap, final Map<String, BigDecimal> nextMap) {
		System.out.println("----- step 2-----");

		while (finalMap.size() > 1) {
			final BigDecimal maxVal = Collections.max(finalMap.values());
			final BigDecimal minVal = Collections.min(finalMap.values());
			System.out.println(maxVal);
			System.out.println(minVal);
			String maxKey = null;
			String minKey = null;
			for (final Object obj : finalMap.entrySet()) {
				Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
				if (entry.getValue().equals(maxVal)) {
					maxKey = entry.getKey();
				}
				if (entry.getValue().equals(minVal)) {
					minKey = entry.getKey();
				}
			}
			if (maxVal.equals(minVal)) {
				nextMap.put(minKey + " pays " + maxKey, minVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
			}
			if (maxVal.compareTo(minVal.abs()) > 0) {
				nextMap.put(minKey + " pays " + maxKey, minVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
				finalMap.put(maxKey, maxVal.add(minVal));
			} else {
				nextMap.put(minKey + " pays " + maxKey, maxVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
				finalMap.put(minKey, minVal.add(maxVal));
			}
		}
	}

	private static BigDecimal getAmountFromGroupedList(List<LedgerExpenses> groupedList, final String entryKey) {
		BigDecimal amt = null;
		boolean isMatched = false;
		for (final LedgerExpenses expense : groupedList) {
			final String Key = expense.getPaidBy().getDisplayName();
			if (!isMatched && Key.equals(entryKey)) {
				amt = expense.getPaidAmount();
				isMatched = true;
			}
		}
		return amt;
	}

	private static List<LedgerExpenses> groupAndSort(final List<LedgerExpenses> expenseList) {
		final Map<String, LedgerExpenses> map = new HashMap<String, LedgerExpenses>();
		for (final LedgerExpenses expense : expenseList) {
			final String key = expense.getPaidBy().getDisplayName();
			if (map.containsKey(key)) {
				final BigDecimal amt = map.get(key).getPaidAmount();
				map.get(key).setPaidAmount(amt.add(expense.getPaidAmount()));
			} else {
				map.put(key, expense);
			}
		}
		final List<LedgerExpenses> finalList = new ArrayList<LedgerExpenses>();
		finalList.addAll(map.values());
		expenseList.clear();
		expenseList.addAll(getSortedData(finalList));
		return expenseList;

	}

	/**
	 * @param list
	 * @return
	 */
	private static List<LedgerExpenses> getSortedData(final List<LedgerExpenses> list) {
		Collections.sort(list, new Comparator<LedgerExpenses>() {
			@Override
			public int compare(final LedgerExpenses o1, final LedgerExpenses o2) {
				return (o2.getPaidAmount()).compareTo(o1.getPaidAmount());
			}
		});
		return list;

	}

}

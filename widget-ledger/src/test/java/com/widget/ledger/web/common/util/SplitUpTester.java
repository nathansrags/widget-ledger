package com.widget.ledger.web.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitUpTester {

	public static void main(String[] args) {
		List<ExpenseList> expenseList = new ArrayList<ExpenseList>();
		addExpense(expenseList);
		calculateIndividualBalances(expenseList);
	}

	private static void calculateIndividualBalances(final List<ExpenseList> expenseList) {
		final Map<String, BigDecimal> userAmtMap = new HashMap<String, BigDecimal>();
		final List<UserList> users = Arrays.asList(UserList.values());
		for (final ExpenseList expense : expenseList) {
			final BigDecimal amt = expense.getAmount();
			BigDecimal splitAmt = amt.divide(new BigDecimal(expense.getForWhom().size()), 2, RoundingMode.HALF_UP);
			for (UserList user : users) {
				if (!userAmtMap.containsKey(user.getUser())) {
					userAmtMap.put(user.getUser(), splitAmt);
				} else {
					userAmtMap.put(user.getUser(), (userAmtMap.get(user.getUser()).add(splitAmt)));
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
		List<ExpenseList> groupedList = groupAndSort(expenseList);
		for (ExpenseList expense : groupedList) {
			System.out.println(expense.getWhoPaid() + " " + expense.getAmount());
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
		executeNextStep(finalMap);
		System.out.println("--- Final Settlement----------");
		for (final Object obj : nextMap.entrySet()) {
			Map.Entry<String, BigDecimal> entry = (Map.Entry) obj;
			System.out.println(entry.getKey() + " $ "+entry.getValue());
		}

	}

	final static Map<String, BigDecimal> nextMap = new HashMap<String, BigDecimal>();

	private static void executeNextStep(final Map<String, BigDecimal> finalMap) {
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

	private static BigDecimal getAmountFromGroupedList(List<ExpenseList> groupedList, final String name) {
		BigDecimal amt = null;
		boolean isMatched = false;
		for (ExpenseList expense : groupedList) {
			if (!isMatched && expense.getWhoPaid().equals(name)) {
				amt = expense.getAmount();
				isMatched = true;
			}
		}
		return amt;
	}

	private static void addExpense(final List<ExpenseList> expenseList) {
		final List<BigDecimal> amounts = new ArrayList<BigDecimal>();
		amounts.add(new BigDecimal("110"));
		amounts.add(new BigDecimal("00"));
		amounts.add(new BigDecimal("0"));
		amounts.add(new BigDecimal("0"));
		amounts.add(new BigDecimal("10"));
		String whoPaid[] = { UserList.GOPI.getUser(), UserList.SIVA.getUser(), UserList.BALA.getUser(),
				UserList.MURUGESH.getUser(),UserList.PUSHPARAJ.getUser() };
		int i = 0;
		for (BigDecimal amount : amounts) {
			ExpenseList expense = new ExpenseList();
			expense.setAmount(amount);
			expense.setWhoPaid(whoPaid[i]);
			expense.setForWhom(Arrays.asList(UserList.values()));
			expenseList.add(expense);
			i++;
		}
		//http://nathanslearning.com/
	}

	private static List<ExpenseList> groupAndSort(final List<ExpenseList> expenseList) {
		final Map<String, ExpenseList> map = new HashMap<String, ExpenseList>();
		for (final ExpenseList expense : expenseList) {
			final String key = expense.getWhoPaid();
			if (map.containsKey(key)) {
				final BigDecimal amt = map.get(key).getAmount();
				map.get(key).setAmount(amt.add(expense.getAmount()));
			} else {
				map.put(key, expense);
			}
		}
		final List<ExpenseList> finalList = new ArrayList<ExpenseList>();
		finalList.addAll(map.values());
		expenseList.clear();
		expenseList.addAll(getSortedData(finalList));
		return expenseList;

	}

	/**
	 * @param list
	 * @return
	 */
	private static List<ExpenseList> getSortedData(final List<ExpenseList> list) {
		Collections.sort(list, new Comparator<ExpenseList>() {
			@Override
			public int compare(final ExpenseList o1, final ExpenseList o2) {
				return (o2.getAmount()).compareTo(o1.getAmount());
			}
		});
		return list;

	}

}

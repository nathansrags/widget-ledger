package com.widget.ledger.web.payment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.to.PaymentDetailsTO;

public class PaymentCalculatorService {
	public PaymentCalculatorService() {

	}

	public PaymentCalculatorService(final List<LedgerExpenses> expenseList) {
		calculatePayment(expenseList);
	}

	public Map<String, PaymentDetailsTO> calculatePayment(final List<LedgerExpenses> expenseList) {
		final Map<String, PaymentDetailsTO> userAmtMap = new HashMap<String, PaymentDetailsTO>();
		for (final LedgerExpenses expense : expenseList) {
			final BigDecimal amt = expense.getPaidAmount();
			final BigDecimal splitAmt = amt.divide(new BigDecimal(expense.getPaidFor().size()), 2,
					RoundingMode.HALF_UP);
			final PaymentDetailsTO detailsTO = new PaymentDetailsTO();
			for (LedgerUserGroups user : expense.getPaidFor()) {
				final String Key = user.getDisplayName();
				if (!userAmtMap.containsKey(Key)) {
					detailsTO.setTotalSpent(splitAmt);
					userAmtMap.put(Key, detailsTO);
				} else {
					// BigDecimal oldAmt = userAmtMap.get(Key).getTotalSpent();
					// System.out.println(oldAmt);
					detailsTO.setTotalSpent(userAmtMap.get(Key).getTotalSpent().add(splitAmt));
					userAmtMap.put(Key, detailsTO);
					// System.out.println(userAmtMap.get(Key).getTotalSpent() +
					// " " + detailsTO.getTotalSpent());
				}
			}
			// System.out.println(splitAmt);
		}
		System.out.println("--- Before Group----------");
		for (final Object obj : userAmtMap.entrySet()) {
			Map.Entry<String, PaymentDetailsTO> entry = (Map.Entry) obj;
			System.out.print("Key: " + entry.getKey());
			System.out.println(", Value: " + entry.getValue().getTotalSpent());
		}
		System.out.println("--- Grouped Data----------");
		List<LedgerExpenses> groupedList = groupAndSort(expenseList);
		for (LedgerExpenses expense : groupedList) {
			System.out.println(expense.getPaidBy().getDisplayName() + " " + expense.getPaidAmount());
		}
		System.out.println("--- After Group----------");
		final Map<String, PaymentDetailsTO> finalMap = new HashMap<String, PaymentDetailsTO>();
		for (final Object obj : userAmtMap.entrySet()) {
			Map.Entry<String, PaymentDetailsTO> entry = (Map.Entry) obj;
			BigDecimal totalSpend = getAmountFromGroupedList(groupedList, entry.getKey());
			PaymentDetailsTO to = entry.getValue();
			BigDecimal oldAmt = to.getTotalSpent();
			to.setTotalSpent(totalSpend.subtract(oldAmt));
			finalMap.put(entry.getKey(), to);
			System.out.print("Key: " + entry.getKey());
			System.out.println(", Value: " + finalMap.get(entry.getKey()).getTotalSpent());
		}
		final Map<String, PaymentDetailsTO> nextMap = new HashMap<String, PaymentDetailsTO>();
		executeNextStep(finalMap, nextMap);
		System.out.println("--- Final Settlement----------");
		for (final Object obj : nextMap.entrySet()) {
			Map.Entry<String, PaymentDetailsTO> entry = (Map.Entry) obj;
			System.out.println(entry.getKey() + " $ " + entry.getValue().getTotalSpent());
		}
		return nextMap;
	}

	private void executeNextStep(final Map<String, PaymentDetailsTO> finalMap,
			final Map<String, PaymentDetailsTO> nextMap) {
		System.out.println("----- step 2-----");

		while (finalMap.size() > 1) {
			List<PaymentDetailsTO> paymentList = (List<PaymentDetailsTO>) finalMap.values();
			final BigDecimal maxVal = Collections.max(finalMap.values(), new Comparator<PaymentDetailsTO>() {
				@Override
				public int compare(PaymentDetailsTO o1, PaymentDetailsTO o2) {
					return o1.getTotalSpent().compareTo(o2.getTotalSpent());
				}
			}).getTotalSpent();
			final BigDecimal minVal = Collections.min(finalMap.values(), new Comparator<PaymentDetailsTO>() {
				@Override
				public int compare(PaymentDetailsTO o1, PaymentDetailsTO o2) {
					return o1.getTotalSpent().compareTo(o2.getTotalSpent());
				}
			}).getTotalSpent();
			System.out.println(maxVal);
			System.out.println(minVal);
			String maxKey = null;
			String minKey = null;
			for (final Object obj : finalMap.entrySet()) {
				Map.Entry<String, PaymentDetailsTO> entry = (Map.Entry) obj;
				if (entry.getValue().equals(maxVal)) {
					maxKey = entry.getKey();
				}
				if (entry.getValue().equals(minVal)) {
					minKey = entry.getKey();
				}
			}
			if (maxVal.equals(minVal)) {
				PaymentDetailsTO to = new PaymentDetailsTO("", BigDecimal.ZERO, BigDecimal.ZERO, maxKey, minKey,
						minVal.abs());
				nextMap.put(minKey + " pays " + maxKey, to);
				//nextMap.put(minKey + " pays " + maxKey, minVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
			}
			if (maxVal.compareTo(minVal.abs()) > 0) {
				PaymentDetailsTO next = new PaymentDetailsTO("", BigDecimal.ZERO, BigDecimal.ZERO, maxKey, minKey,
						minVal.abs());
				nextMap.put(minKey + " pays " + maxKey, next);
				//nextMap.put(minKey + " pays " + maxKey, minVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
				PaymentDetailsTO finalTO = new PaymentDetailsTO("", BigDecimal.ZERO, BigDecimal.ZERO, maxKey, minKey,
						minVal.abs());
				//finalMap.put(maxKey, maxVal.add(minVal));
			} else {
				//nextMap.put(minKey + " pays " + maxKey, maxVal.abs());
				finalMap.remove(maxKey);
				finalMap.remove(minKey);
				//finalMap.put(minKey, minVal.add(maxVal));
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

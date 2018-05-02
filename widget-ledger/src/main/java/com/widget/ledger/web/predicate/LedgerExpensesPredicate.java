package com.widget.ledger.web.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.widget.ledger.web.domain.QLedgerExpenses;

public class LedgerExpensesPredicate {

	private static QLedgerExpenses qLedgerExpenses = QLedgerExpenses.ledgerExpenses;

	public static BooleanExpression byID(final long id) {
		return qLedgerExpenses.expenseId.eq((int)id);
	}

	public static BooleanExpression byJoinKey(final String key) {
		return qLedgerExpenses.expenseSheet.ledgerUniqueId.eq(key);
	}
}

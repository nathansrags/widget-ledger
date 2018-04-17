package com.widget.ledger.web.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.widget.ledger.web.domain.QLedgerSheets;

public class LedgerSheetsPredicate {

	private static QLedgerSheets qLedgerSheets = QLedgerSheets.ledgerSheets;

	public static BooleanExpression forSheet(final String uniqueId) {
		return qLedgerSheets.ledgerUniqueId.eq(uniqueId);
	}

}

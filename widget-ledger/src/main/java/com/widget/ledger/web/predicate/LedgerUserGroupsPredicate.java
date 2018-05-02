package com.widget.ledger.web.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.widget.ledger.web.domain.QLedgerUserGroups;

public class LedgerUserGroupsPredicate {

	private static QLedgerUserGroups qLedgerUserGroups = QLedgerUserGroups.ledgerUserGroups;

	public static BooleanExpression byId(final long id) {
		return qLedgerUserGroups.ledgerUserId.eq((int)id);
	}

	public static BooleanExpression byJoinKey(final String key) {
		return qLedgerUserGroups.userSheet.ledgerUniqueId.eq(key);
	}
}

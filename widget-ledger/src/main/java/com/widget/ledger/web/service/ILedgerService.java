package com.widget.ledger.web.service;

import com.widget.ledger.web.domain.LedgerSheets;

public interface ILedgerService {

	LedgerSheets getLedgerSheetByUniqueId(final String uniqueId);

	LedgerSheets saveOrUpdateSheet(final LedgerSheets ledgerSheets);

	void updateUserGroup();

	void saveLedgerExpense();

	void saveUserGroup();

}

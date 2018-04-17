package com.widget.ledger.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.predicate.LedgerSheetsPredicate;
import com.widget.ledger.web.repository.LedgerExpensesRepository;
import com.widget.ledger.web.repository.LedgerSheetRepository;
import com.widget.ledger.web.repository.LedgerUserGroupsRepository;
import com.widget.ledger.web.service.ILedgerService;

@Service("ledgerServiceImpl")
public class LedgerServiceImpl implements ILedgerService {

	@Autowired
	private LedgerSheetRepository sheetRepository;

	@Autowired
	private LedgerExpensesRepository expenseRepository;

	@Autowired
	private LedgerUserGroupsRepository userRepository;

	@Override
	public LedgerSheets getLedgerSheetByUniqueId(final String uniqueId) {
		// BX4NWXYGHYRF
		LedgerSheets s = sheetRepository.findOne(LedgerSheetsPredicate.forSheet(uniqueId)); 
		return s;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LedgerSheets saveOrUpdateSheet(final LedgerSheets ledgerSheets) {
		// final LedgerSheets ledgerSheets =
		// getLedgerSheetByUniqueId("BX4NWXYGHYRF");
		ledgerSheets.setSysActionCd("U");
		ledgerSheets.setLedgerSheetName("Test Sheet");
		// ledgerSheets.setLedgerUniqueId(SecureIdGeneratorService.generateUID());
		return sheetRepository.save(ledgerSheets);
	}

	@Override
	public void saveLedgerExpense() {
		LedgerExpenses ledgerExpenses = new LedgerExpenses();
		ledgerExpenses.setLedgerSheet(sheetRepository.findOne((long) 1));
		ledgerExpenses.setPaidAmount(new BigDecimal(200.00));
		ledgerExpenses.setPaidBy("Senthil");
		ledgerExpenses.setPaidFor("Gopi");
		expenseRepository.save(ledgerExpenses);
	}

	@Override
	public void saveUserGroup() {
		LedgerUserGroups user = new LedgerUserGroups();
		user.setUserName("Gopi");
		user.setLedgerSheet(sheetRepository.findOne((long) 1));
		user.setSysActionCd("I");
		user.setSysActionId("ADD_USER");
		user.setSysActionNm("ADD_USER");
		List<LedgerUserGroups> userList = new ArrayList<LedgerUserGroups>();
		userRepository.save(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void updateUserGroup() {
		final LedgerSheets sheets = sheetRepository.findOne((long) 1);
		List<LedgerUserGroups> userList = sheets.getRelatedUsers();
		for (LedgerUserGroups ledgerUserGroups : userList) {
			ledgerUserGroups.setSysActionCd("U");
			userRepository.save(ledgerUserGroups);
		}

	}

}

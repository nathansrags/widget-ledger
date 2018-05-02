package com.widget.ledger.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.predicate.LedgerSheetsPredicate;
import com.widget.ledger.web.repository.LedgerSheetRepository;
import com.widget.ledger.web.service.ILedgerService;

@Service("ledgerSheetServiceImpl")
public class LedgerSheetServiceImpl implements ILedgerService<LedgerSheets> {

	@Autowired
	private LedgerSheetRepository sheetRepository;

	@Override
	public LedgerSheets findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerSheets findOne(final String uniqueId) {
		// BX4NWXYGHYRF
		return sheetRepository.findOne(LedgerSheetsPredicate.forSheet(uniqueId));
	}

	@Override
	public List<LedgerSheets> findByCol(final String uniqueId) {
		// BX4NWXYGHYRF
		return sheetRepository.findAll();
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public LedgerSheets saveOrUpdateSheet(final LedgerSheets ledgerSheets) {
	 * // final LedgerSheets ledgerSheets = //
	 * getLedgerSheetByUniqueId("BX4NWXYGHYRF");
	 * ledgerSheets.setSysActionCd("U"); ledgerSheets.setLedgerSheetName(
	 * "Test Sheet"); //
	 * ledgerSheets.setLedgerUniqueId(SecureIdGeneratorService.generateUID());
	 * return sheetRepository.save(ledgerSheets); }
	 * 
	 * @Override public LedgerExpenses saveLedgerExpense() { LedgerExpenses
	 * ledgerExpenses = new LedgerExpenses();
	 * ledgerExpenses.setLedgerSheet(sheetRepository.findOne((long) 1));
	 * ledgerExpenses.setPaidAmount(new BigDecimal(200.00));
	 * ledgerExpenses.setPaidBy("Senthil"); ledgerExpenses.setPaidFor("Gopi");
	 * return expenseRepository.save(ledgerExpenses); }
	 */

	@Override
	public LedgerSheets find(LedgerSheets entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerSheets> findAll() {
		return sheetRepository.findAll();
	}
	
	@Override
	public List<LedgerSheets> findByIds(List<Integer> ids) {
		return sheetRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LedgerSheets saveOrUpdate(LedgerSheets entity) {
		return sheetRepository.save(entity);
	}

	@Override
	public void delete(LedgerSheets entity) {
		sheetRepository.delete(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<LedgerSheets> saveAll(List<LedgerSheets> entity) {
		for (LedgerSheets ledgerSheets : entity) {
			sheetRepository.saveAndFlush(ledgerSheets);
		}
		return this.findAll();
	}

}

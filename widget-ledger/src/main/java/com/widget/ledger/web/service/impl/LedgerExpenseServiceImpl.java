package com.widget.ledger.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Lists;
import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.predicate.LedgerExpensesPredicate;
import com.widget.ledger.web.repository.LedgerExpensesRepository;
import com.widget.ledger.web.service.ILedgerService;

@Service("ledgerExpenseServiceImpl")
public class LedgerExpenseServiceImpl implements ILedgerService<LedgerExpenses> {

	@Autowired
	private LedgerExpensesRepository expenseRepository;

	@Override
	public LedgerExpenses find(final LedgerExpenses entity) {
		final Integer id = entity.getExpenseId();
		return expenseRepository.findOne(LedgerExpensesPredicate.byID(id));
	}

	@Override
	public LedgerExpenses findById(long id) {
		return expenseRepository.findOne(LedgerExpensesPredicate.byID(id));
	}

	@Override
	public LedgerExpenses findOne(final String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerExpenses> findByCol(final String column) {
		List<LedgerExpenses> expenses = Lists
				.newArrayList(expenseRepository.findAll(LedgerExpensesPredicate.byJoinKey(column)));
		return expenses;
	}

	@Override
	public List<LedgerExpenses> findByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerExpenses> findAll() {
		return expenseRepository.findAll();
	}

	@Override
	public LedgerExpenses saveOrUpdate(final LedgerExpenses entity) {
		return expenseRepository.save(entity);
	}

	@Override
	public void delete(final LedgerExpenses entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LedgerExpenses> saveAll(List<LedgerExpenses> entity) {
		for (LedgerExpenses ledgerExpenses : entity) {
			expenseRepository.saveAndFlush(ledgerExpenses);
		}
		return this.findAll();
	}

}

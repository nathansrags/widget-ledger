package com.widget.ledger.web.transformer.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.widget.ledger.web.common.util.ObjectUtility;
import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.to.ExpenseDetailsTO;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@Component("ledgerExpenseTranformerImpl")
public class LedgerExpenseTranformerImpl implements IGenericTransformer<ExpenseDetailsTO, LedgerExpenses> {

	@Autowired
	@Qualifier("ledgerUserGroupTransformerImpl")
	private IGenericTransformer<UserDetailsTO, LedgerUserGroups> ledgerUserGroupTransformerImpl;

	@Override
	public LedgerExpenses transformTOtoEntityForUpdate(ExpenseDetailsTO to, LedgerExpenses entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerExpenses transformTOtoEntity(ExpenseDetailsTO to) {
		LedgerExpenses expense = new LedgerExpenses();
		expense.setExpenseDesc(to.getExpenseDesc());
		expense.setPaidAmount(to.getPaidAmt());
		try {
			expense.setPaidDate(ObjectUtility.convertStringtoSqlDate(to.getExpenseDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		expense.setPaidBy(ledgerUserGroupTransformerImpl.transformTOtoEntity(to.getPaidBy()));
		expense.setPaidFor(
				new HashSet<LedgerUserGroups>(ledgerUserGroupTransformerImpl.transformTOtoEntity(to.getPaidFor())));
		updateAuditColumns(expense);
		return expense;
	}

	@Override
	public ExpenseDetailsTO transformEntityToTO(LedgerExpenses entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerExpenses> transformTOtoEntityForUpdate(List<ExpenseDetailsTO> to, List<LedgerExpenses> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerExpenses> transformTOtoEntity(List<ExpenseDetailsTO> to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpenseDetailsTO> transformEntityToTO(List<LedgerExpenses> entity) {
		List<ExpenseDetailsTO> expenseDetailsTO = new ArrayList<ExpenseDetailsTO>();
		for (final LedgerExpenses expense : entity) {
			ExpenseDetailsTO detailsTO = new ExpenseDetailsTO();
			detailsTO.setExpenseDate(expense.getPaidDate().toString());
			detailsTO.setPaidAmt(expense.getPaidAmount());
			detailsTO.setExpenseDesc(expense.getExpenseDesc());
			detailsTO.setPaidBy(ledgerUserGroupTransformerImpl.transformEntityToTO(expense.getPaidBy()));
			detailsTO.setPaidFor(ledgerUserGroupTransformerImpl
					.transformEntityToTO(new ArrayList<LedgerUserGroups>(expense.getPaidFor())));
			expenseDetailsTO.add(detailsTO);
		}
		return expenseDetailsTO;
	}

	@Override
	public ExpenseDetailsTO transformListEntityToTO(List<LedgerExpenses> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerExpenses updateAuditColumns(LedgerExpenses entity) {
		entity.setSysActionCd("I");
		entity.setSysActionId("SYSTEM");
		entity.setSysActionNm("SYSTEM");
		return entity;
	}

}

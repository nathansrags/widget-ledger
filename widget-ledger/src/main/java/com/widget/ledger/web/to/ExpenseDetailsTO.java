package com.widget.ledger.web.to;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseDetailsTO {

	private Long expenseDetailId;
	private String expenseDate;
	private String expenseDesc;
	private UserDetailsTO paidBy;
	private BigDecimal paidAmt;
	private List<UserDetailsTO> paidFor;

	public Long getExpenseDetailId() {
		return expenseDetailId;
	}

	public void setExpenseDetailId(Long expenseDetailId) {
		this.expenseDetailId = expenseDetailId;
	}

	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getExpenseDesc() {
		return expenseDesc;
	}

	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}

	public UserDetailsTO getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(UserDetailsTO paidBy) {
		this.paidBy = paidBy;
	}

	public BigDecimal getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(BigDecimal paidAmt) {
		this.paidAmt = paidAmt;
	}

	public List<UserDetailsTO> getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(List<UserDetailsTO> paidFor) {
		this.paidFor = paidFor;
	}

}

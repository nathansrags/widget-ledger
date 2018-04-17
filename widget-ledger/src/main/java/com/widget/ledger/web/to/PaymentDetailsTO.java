package com.widget.ledger.web.to;

import java.math.BigDecimal;

/**
 * @author 175049
 *
 */
public class PaymentDetailsTO {

	private String name;
	private BigDecimal expense;
	private BigDecimal totalSpent;
	private String ownedTo;
	private String ownedFrom;
	private BigDecimal balaneAmt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getExpense() {
		return expense;
	}

	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}

	public BigDecimal getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(BigDecimal totalSpent) {
		this.totalSpent = totalSpent;
	}

	public String getOwnedTo() {
		return ownedTo;
	}

	public void setOwnedTo(String ownedTo) {
		this.ownedTo = ownedTo;
	}

	public String getOwnedFrom() {
		return ownedFrom;
	}

	public void setOwnedFrom(String ownedFrom) {
		this.ownedFrom = ownedFrom;
	}

	public BigDecimal getBalaneAmt() {
		return balaneAmt;
	}

	public void setBalaneAmt(BigDecimal balaneAmt) {
		this.balaneAmt = balaneAmt;
	}

}

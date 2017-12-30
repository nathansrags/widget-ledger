package com.widget.ledger.web.common.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 175049
 *
 */
public class ExpenseList {

	private String description;
	private BigDecimal amount;
	private String whoPaid;
	private List<UserList> forWhom;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getWhoPaid() {
		return whoPaid;
	}

	public void setWhoPaid(String whoPaid) {
		this.whoPaid = whoPaid;
	}

	public List<UserList> getForWhom() {
		return forWhom;
	}

	public void setForWhom(List<UserList> forWhom) {
		this.forWhom = forWhom;
	}

	@Override
	public String toString() {
		StringBuffer forWhomList = new StringBuffer();
		for (final UserList userList : forWhom) {
			forWhomList.append(userList.getUser());
			forWhomList.append("-");
		}
		return amount + "| " + whoPaid + "| " + forWhomList.toString();
	}

}

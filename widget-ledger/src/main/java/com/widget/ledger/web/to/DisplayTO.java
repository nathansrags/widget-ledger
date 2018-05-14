package com.widget.ledger.web.to;

import java.util.List;

/**
 * @author 175049
 *
 */
public class DisplayTO {

	private List<UserDetailsTO> userDetailsTO;

	private List<ExpenseDetailsTO> expenseDetailsTO;

	private List<PaymentDetailsTO> paymentDetailsTO;

	private List<PaymentsTO> paymentTO;

	private String createdOn;
	private String lastUpdated;
	private String totalUsers;
	private String totalExpenses;
	private String countExpenses;
	private String uniqueSheetId;
	private String sheetName;

	public List<UserDetailsTO> getUserDetailsTO() {
		return userDetailsTO;
	}

	public void setUserDetailsTO(List<UserDetailsTO> userDetailsTO) {
		this.userDetailsTO = userDetailsTO;
	}

	public List<ExpenseDetailsTO> getExpenseDetailsTO() {
		return expenseDetailsTO;
	}

	public void setExpenseDetailsTO(List<ExpenseDetailsTO> expenseDetailsTO) {
		this.expenseDetailsTO = expenseDetailsTO;
	}

	public String getUniqueSheetId() {
		return uniqueSheetId;
	}

	public void setUniqueSheetId(String uniqueSheetId) {
		this.uniqueSheetId = uniqueSheetId;
	}

	public List<PaymentDetailsTO> getPaymentDetailsTO() {
		return paymentDetailsTO;
	}

	public void setPaymentDetailsTO(List<PaymentDetailsTO> paymentDetailsTO) {
		this.paymentDetailsTO = paymentDetailsTO;
	}

	public List<PaymentsTO> getPaymentTO() {
		return paymentTO;
	}

	public void setPaymentTO(List<PaymentsTO> paymentTO) {
		this.paymentTO = paymentTO;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
	}

	public String getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(String totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public String getCountExpenses() {
		return countExpenses;
	}

	public void setCountExpenses(String countExpenses) {
		this.countExpenses = countExpenses;
	}

}

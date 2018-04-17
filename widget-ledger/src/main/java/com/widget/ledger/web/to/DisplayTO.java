package com.widget.ledger.web.to;

import java.util.List;

/**
 * @author 175049
 *
 */
public class DisplayTO {

	private List<UserDetailsTO> userDetailsTO;

	private List<ExpenseDetailsTO> expenseDetailsTO;

	private List<PaymentDetailsTO> paymentTO;

	private String createdOn;
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

	public List<PaymentDetailsTO> getPaymentTO() {
		return paymentTO;
	}

	public void setPaymentTO(List<PaymentDetailsTO> paymentTO) {
		this.paymentTO = paymentTO;
	}

	public String getUniqueSheetId() {
		return uniqueSheetId;
	}

	public void setUniqueSheetId(String uniqueSheetId) {
		this.uniqueSheetId = uniqueSheetId;
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

}

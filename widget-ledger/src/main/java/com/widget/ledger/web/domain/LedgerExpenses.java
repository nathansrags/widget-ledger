package com.widget.ledger.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LEDGER_EXPENSES")
@Cacheable
public class LedgerExpenses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXPENSE_ID")
	private int expenseId;

	@Column(name = "EXPENSE_DESC")
	private String expenseDesc;

	@Column(name = "PAID_AMOUNT")
	private BigDecimal paidAmount;

	@Column(name = "PAID_BY")
	private String paidBy;

	@Column(name = "PAID_FOR")
	private String paidFor;

	@Column(name = "SYS_START_TS")
	private Timestamp sysStartTs;

	@Column(name = "SYS_ACTION_ID")
	private String sysActionId;

	@Column(name = "SYS_ACTION_NM")
	private String sysActionNm;

	@Column(name = "SYS_ACTION_CD")
	private String sysActionCd;

	@Column(name = "LAST_UPDATED_TS")
	private Timestamp lastUpdateTs;

	@ManyToOne
	@JoinColumn(name = "LEDGER_SHEETS_ID")
	private LedgerSheets ledgerSheet;

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseDesc() {
		return expenseDesc;
	}

	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public String getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(String paidFor) {
		this.paidFor = paidFor;
	}

	public Timestamp getSysStartTs() {
		return sysStartTs;
	}

	public void setSysStartTs(Timestamp sysStartTs) {
		this.sysStartTs = sysStartTs;
	}

	public String getSysActionId() {
		return sysActionId;
	}

	public void setSysActionId(String sysActionId) {
		this.sysActionId = sysActionId;
	}

	public String getSysActionNm() {
		return sysActionNm;
	}

	public void setSysActionNm(String sysActionNm) {
		this.sysActionNm = sysActionNm;
	}

	public String getSysActionCd() {
		return sysActionCd;
	}

	public void setSysActionCd(String sysActionCd) {
		this.sysActionCd = sysActionCd;
	}

	public Timestamp getLastUpdateTs() {
		return lastUpdateTs;
	}

	public void setLastUpdateTs(Timestamp lastUpdateTs) {
		this.lastUpdateTs = lastUpdateTs;
	}

	public LedgerSheets getLedgerSheet() {
		return ledgerSheet;
	}

	public void setLedgerSheet(LedgerSheets ledgerSheet) {
		this.ledgerSheet = ledgerSheet;
	}

}

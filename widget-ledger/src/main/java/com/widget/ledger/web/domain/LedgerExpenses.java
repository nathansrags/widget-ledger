package com.widget.ledger.web.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private Long expenseId;

	@Column(name = "EXPENSE_DESC")
	private String expenseDesc;

	@Column(name = "PAID_AMOUNT")
	private BigDecimal paidAmount;
	
	@Column(name="PAID_DATE")
	private Date paidDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="PAID_BY")
	private LedgerUserGroups paidBy;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "LEDGER_USER_PAID_FOR", 
        joinColumns = { @JoinColumn(name = "EXPENSE_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "LEDGER_USER_ID") }
    )
	private Set<LedgerUserGroups> paidFor;

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
	private LedgerSheets expenseSheet;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseDesc() {
		return expenseDesc;
	}

	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	
	public LedgerUserGroups getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(LedgerUserGroups paidBy) {
		this.paidBy = paidBy;
	}

	public Set<LedgerUserGroups> getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(Set<LedgerUserGroups> paidFor) {
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

	public LedgerSheets getExpenseSheet() {
		return expenseSheet;
	}

	public void setExpenseSheet(final LedgerSheets expenseSheet) {
		this.expenseSheet = expenseSheet;
	}

	

}

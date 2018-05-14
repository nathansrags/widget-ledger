package com.widget.ledger.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 175049
 *
 */
@Entity
@Table(name = "LEDGER_USER_GROUPS")
public class LedgerUserGroups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LEDGER_USER_ID")
	private Long ledgerUserId;

	@Column(name = "LEDGER_USER_NAME")
	private String userName;

	@Column(name = "LEDGER_DISPLAY_NAME")
	private String displayName;

	@Column(name = "LEDGER_USER_COMMENTS")
	private String ledgerUserCommnets;

	@Column(name = "LEDGER_BALANCED_AMT")
	private BigDecimal balancedAmt;

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
	private LedgerSheets userSheet;

/*	@ManyToMany(mappedBy = "paidFor")
	private List<LedgerExpenses> paidFor;
*/	
	public Long getLedgerUserId() {
		return ledgerUserId;
	}

	public void setLedgerUserId(Long ledgerUserId) {
		this.ledgerUserId = ledgerUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public BigDecimal getBalancedAmt() {
		return balancedAmt;
	}

	public void setBalancedAmt(BigDecimal balancedAmt) {
		this.balancedAmt = balancedAmt;
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

	public LedgerSheets getUserSheet() {
		return userSheet;
	}

	public void setUserSheet(LedgerSheets userSheet) {
		this.userSheet = userSheet;
	}

	public String getLedgerUserCommnets() {
		return ledgerUserCommnets;
	}

	public void setLedgerUserCommnets(final String ledgerUserCommnets) {
		this.ledgerUserCommnets = ledgerUserCommnets;
	}

	/*public List<LedgerExpenses> getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(final List<LedgerExpenses> paidFor) {
		this.paidFor = paidFor;
	}

	@OneToOne(fetch=FetchType.LAZY)
	private LedgerExpenses paidBy;

	public LedgerExpenses getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(LedgerExpenses paidBy) {
		this.paidBy = paidBy;
	}*/
	
}

package com.widget.ledger.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LEDGER_SHEETS")
@Cacheable
public class LedgerSheets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7443758039633036832L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LEDGER_SHEETS_ID")
	private Long ledgerSheetId;

	@Column(name = "LEDGER_SHEETS_NAME")
	private String ledgerSheetName;

	@Column(name = "LEDGER_UNIQUE_SHEET_ID")
	private String ledgerUniqueId;

	@Column(name = "SYS_START_TS")
	private Timestamp sysStartTs;

	@Column(name = "SYS_ACTION_ID")
	private String sysActionId;

	@Column(name = "SYS_ACTION_NM")
	private String sysActionNm;

	@Column(name = "SYS_ACTION_CD")
	private String sysActionCd;

	@Column(name = "LAST_UPDATE_TS")
	private Timestamp lastUpdateTs;

	@OneToMany(mappedBy = "userSheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<LedgerUserGroups> relatedUsers = new ArrayList<LedgerUserGroups>();

	@OneToMany(mappedBy = "expenseSheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<LedgerExpenses> relatedExpenses = new ArrayList<LedgerExpenses>();

	public Long getLedgerSheetId() {
		return ledgerSheetId;
	}

	public void setLedgerSheetId(Long ledgerSheetId) {
		this.ledgerSheetId = ledgerSheetId;
	}

	public String getLedgerSheetName() {
		return ledgerSheetName;
	}

	public void setLedgerSheetName(String ledgerSheetName) {
		this.ledgerSheetName = ledgerSheetName;
	}

	public String getLedgerUniqueId() {
		return ledgerUniqueId;
	}

	public void setLedgerUniqueId(String ledgerUniqueId) {
		this.ledgerUniqueId = ledgerUniqueId;
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

	public List<LedgerExpenses> getRelatedExpenses() {
		return relatedExpenses;
	}

	public void setRelatedExpenses(List<LedgerExpenses> relatedExpenses) {
		this.relatedExpenses = relatedExpenses;
	}

	public List<LedgerUserGroups> getRelatedUsers() {
		return relatedUsers;
	}

	public void setRelatedUsers(List<LedgerUserGroups> relatedUsers) {
		this.relatedUsers = relatedUsers;
	}

}

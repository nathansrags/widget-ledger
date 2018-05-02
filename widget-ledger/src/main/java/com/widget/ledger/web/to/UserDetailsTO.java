package com.widget.ledger.web.to;

/**
 * @author 175049
 *
 */
public class UserDetailsTO {

	private Long userDetailId;
	private String addPersonName;
	private String addDisplayName;
	private String addComments;
	private String uniqueSheetId;
	private String lastUpdateTime;

	public Long getUserDetailId() {
		return userDetailId;
	}

	public void setUserDetailId(Long userDetailId) {
		this.userDetailId = userDetailId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public String getAddDisplayName() {
		return addDisplayName;
	}

	public void setAddDisplayName(String addDisplayName) {
		this.addDisplayName = addDisplayName;
	}

	public String getAddComments() {
		return addComments;
	}

	public void setAddComments(String addComments) {
		this.addComments = addComments;
	}

	public String getUniqueSheetId() {
		return uniqueSheetId;
	}

	public void setUniqueSheetId(String uniqueSheetId) {
		this.uniqueSheetId = uniqueSheetId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}

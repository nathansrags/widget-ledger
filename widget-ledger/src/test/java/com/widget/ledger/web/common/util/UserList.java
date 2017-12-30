package com.widget.ledger.web.common.util;

/**
 * @author 175049
 *
 */
public enum UserList {

	GOPI("Aaron"),
	SIVA("Bob"),
	BALA("Craig"),
	MURUGESH("David"),
	PUSHPARAJ("Eddie");

	private UserList(final String user) {
		this.user = user;
	}

	private String user;

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return this.toString();
	}

}

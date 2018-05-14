package com.widget.ledger.web.exceptions;

public class BusinessUncheckedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8990213326794817979L;

	public BusinessUncheckedException() {
		super();
	}

	public BusinessUncheckedException(String s) {
		super(s);
	}

	public BusinessUncheckedException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public BusinessUncheckedException(Throwable throwable) {
		super(throwable);
	}
}

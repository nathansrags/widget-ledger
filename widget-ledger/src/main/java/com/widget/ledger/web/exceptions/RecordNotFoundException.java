package com.widget.ledger.web.exceptions;

public class RecordNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7829116365683666785L;

	public RecordNotFoundException() {
		super();
	}

	public RecordNotFoundException(String s) {
		super(s);
	}

	public RecordNotFoundException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public RecordNotFoundException(Throwable throwable) {
		super(throwable);
	}
}

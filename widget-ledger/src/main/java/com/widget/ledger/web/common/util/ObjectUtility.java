package com.widget.ledger.web.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 175049
 *
 */
public class ObjectUtility {

	/**
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringtoDate(final String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Date date = formatter.parse(dateString);
		// System.out.println(formatter.format(date));
		return date;

	}

	/**
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date convertStringtoSqlDate(final String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Date date = formatter.parse(dateString);
		return new java.sql.Date(date.getTime());

	}
}

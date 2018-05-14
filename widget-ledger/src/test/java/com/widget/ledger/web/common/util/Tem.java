package com.widget.ledger.web.common.util;

import java.math.BigDecimal;

public class Tem {

	public static void main(String[] args) {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(new BigDecimal("50.00"));
		System.out.println(total);
	}

}

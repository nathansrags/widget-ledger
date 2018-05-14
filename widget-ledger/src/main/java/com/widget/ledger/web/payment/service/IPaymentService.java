package com.widget.ledger.web.payment.service;

import java.util.List;

public interface IPaymentService<E> {

	void calculatePayment(List<E> e);
}

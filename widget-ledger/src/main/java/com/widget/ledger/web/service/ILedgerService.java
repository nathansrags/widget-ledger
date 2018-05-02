package com.widget.ledger.web.service;

import java.util.List;

public interface ILedgerService<E> {

	E find(E entity);

	List<E> findAll();

	E findById(long id);

	E findOne(String column);

	E saveOrUpdate(E entity);

	List<E> saveAll(List<E> entity);

	void delete(E entity);
	
	List<E> findByCol(String column);
	
	List<E> findByIds(List<Integer> ids);

}

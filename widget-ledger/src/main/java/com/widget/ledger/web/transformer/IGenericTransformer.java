package com.widget.ledger.web.transformer;

import java.util.List;

public interface IGenericTransformer<T, E> {
	/**
	 * @param to
	 * @param entity
	 * @return
	 */
	E transformTOtoEntityForUpdate(T to, E entity);

	/**
	 * @param to
	 * @return
	 */
	E transformTOtoEntity(T to);

	/**
	 * @param entity
	 * @return
	 */
	T transformEntityToTO(E entity);
	
	/**
	 * @param to
	 * @param entity
	 * @return
	 */
	List<E> transformTOtoEntityForUpdate(List<T> to, List<E> entity);

	/**
	 * @param to
	 * @return
	 */
	List<E> transformTOtoEntity(List<T> to);
	
	/**
	 * @param entity
	 * @return
	 */
	List<T> transformEntityToTO(List<E> entity);
	
	/**
	 * @param entity
	 * @return
	 */
	T transformListEntityToTO(List<E> entity);
	
	/**
	 * @param entity
	 * @return
	 */
	E updateAuditColumns(E entity);
	
	
}

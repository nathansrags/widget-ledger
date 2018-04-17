package com.widget.ledger.web.transformer;

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
	
}

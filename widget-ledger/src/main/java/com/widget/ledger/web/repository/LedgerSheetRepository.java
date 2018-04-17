package com.widget.ledger.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.widget.ledger.web.domain.LedgerSheets;

/**
 * @author 175049
 *
 *
 */
@Repository
public interface LedgerSheetRepository
		extends JpaRepository<LedgerSheets, Long>, QueryDslPredicateExecutor<LedgerSheets> {

	/*
	 * @Modifying(clearAutomatically = true)
	 * 
	 * @Query(
	 * "UPDATE LEDGER_SHEETS S SET S.LEDGER_SHEETS_NAME = :sheetName WHERE S.LEDGER_UNIQUE_SHEET_ID = :sheetId"
	 * ) int updateSheetName(@Param("sheetName") String
	 * sheetName, @Param("sheetId") String sheetId);
	 */

}

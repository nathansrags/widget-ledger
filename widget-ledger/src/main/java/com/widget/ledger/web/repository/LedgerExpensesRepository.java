package com.widget.ledger.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.widget.ledger.web.domain.LedgerExpenses;

@Repository
public interface LedgerExpensesRepository
		extends JpaRepository<LedgerExpenses, Long>, QueryDslPredicateExecutor<LedgerExpenses> {

}

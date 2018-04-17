package com.widget.ledger.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.widget.ledger.web.domain.LedgerUserGroups;

public interface LedgerUserGroupsRepository
		extends JpaRepository<LedgerUserGroups, Long>, QueryDslPredicateExecutor<LedgerUserGroups> {

}

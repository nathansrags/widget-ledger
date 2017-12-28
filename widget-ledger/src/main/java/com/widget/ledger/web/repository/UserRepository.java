package com.widget.ledger.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.widget.ledger.web.domain.User;

public interface UserRepository extends JpaRepository<User, Long>,
		QueryDslPredicateExecutor<User> {

}

package com.widget.ledger.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.predicate.LedgerUserGroupsPredicate;
import com.widget.ledger.web.repository.LedgerUserGroupsRepository;
import com.widget.ledger.web.service.ILedgerService;

@Service("ledgerUserGroupServiceImpl")
public class LedgerUserGroupServiceImpl implements ILedgerService<LedgerUserGroups> {

	@Autowired
	private LedgerUserGroupsRepository ledgerUserGroupRepository;

	@Override
	public LedgerUserGroups findById(long id) {
		return ledgerUserGroupRepository.findOne(LedgerUserGroupsPredicate.byId(id));
	}

	@Override
	public LedgerUserGroups findOne(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerUserGroups> findByCol(String column) {
		return Lists.newArrayList(ledgerUserGroupRepository.findAll(LedgerUserGroupsPredicate.byJoinKey(column)));

	}

	@Override
	public LedgerUserGroups find(final LedgerUserGroups entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public LedgerUserGroups saveOrUpdate(final LedgerUserGroups entity) {
		return ledgerUserGroupRepository.saveAndFlush(entity);
	}

	@Override
	public void delete(final LedgerUserGroups entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LedgerUserGroups> findAll() {
		return ledgerUserGroupRepository.findAll();
	}
	
	@Override
	public List<LedgerUserGroups> findByIds(List<Integer> ids) {
		return ledgerUserGroupRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LedgerUserGroups> saveAll(final List<LedgerUserGroups> entity) {
		for (final LedgerUserGroups ledgerUserGroups : entity) {
			ledgerUserGroupRepository.save(ledgerUserGroups);
		}
		return ledgerUserGroupRepository.findAll();
	}

}

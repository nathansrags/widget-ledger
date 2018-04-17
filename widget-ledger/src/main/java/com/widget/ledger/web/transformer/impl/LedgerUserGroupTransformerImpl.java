package com.widget.ledger.web.transformer.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@Component("ledgerUserGroupTransformerImpl")
public class LedgerUserGroupTransformerImpl implements IGenericTransformer<List<UserDetailsTO>, List<LedgerUserGroups>> {

	@Override
	public List<LedgerUserGroups> transformTOtoEntityForUpdate(List<UserDetailsTO> to, List<LedgerUserGroups> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerUserGroups> transformTOtoEntity(List<UserDetailsTO> to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailsTO> transformEntityToTO(List<LedgerUserGroups> entity) {
		// TODO Auto-generated method stub
		return null;
	}

}

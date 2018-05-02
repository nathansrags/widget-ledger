package com.widget.ledger.web.transformer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.to.DisplayTO;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@Component("ledgerSheetTransformerImpl")
public class LedgerSheetTransformerImpl implements IGenericTransformer<DisplayTO, LedgerSheets> {

	@Autowired
	@Qualifier("ledgerUserGroupTransformerImpl")
	private IGenericTransformer<UserDetailsTO, LedgerUserGroups> userGroupTransformerImpl;

	@Override
	public LedgerSheets transformTOtoEntityForUpdate(final DisplayTO to, final LedgerSheets entity) {
		return null;
	}

	@Override
	public LedgerSheets transformTOtoEntity(final DisplayTO to) {
		LedgerSheets sheets = new LedgerSheets();
		sheets.setLedgerUniqueId(to.getUniqueSheetId());
		sheets.setLedgerSheetName(to.getSheetName());
		updateAuditColumns(sheets);
		return sheets;
	}

	@Override
	public DisplayTO transformEntityToTO(final LedgerSheets entity) {
		final DisplayTO displayTO = new DisplayTO();
		displayTO.setSheetName(entity.getLedgerSheetName());
		displayTO.setUniqueSheetId(entity.getLedgerUniqueId());
		List<UserDetailsTO> userList = userGroupTransformerImpl.transformEntityToTO(entity.getRelatedUsers());
		displayTO.setUserDetailsTO(userList);
		return displayTO;
	}

	@Override
	public List<LedgerSheets> transformTOtoEntityForUpdate(List<DisplayTO> to, List<LedgerSheets> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LedgerSheets> transformTOtoEntity(List<DisplayTO> to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DisplayTO> transformEntityToTO(List<LedgerSheets> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisplayTO transformListEntityToTO(List<LedgerSheets> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerSheets updateAuditColumns(final LedgerSheets ledgerSheets) {
		ledgerSheets.setSysActionCd("I");
		ledgerSheets.setSysActionId("SYSTEM");
		ledgerSheets.setSysActionNm("SYSTEM");
		return ledgerSheets;
	}

}

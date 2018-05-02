package com.widget.ledger.web.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.domain.LedgerUserGroups;
import com.widget.ledger.web.service.ILedgerService;
import com.widget.ledger.web.to.UserDetailsTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

@Component("ledgerUserGroupTransformerImpl")
public class LedgerUserGroupTransformerImpl implements IGenericTransformer<UserDetailsTO, LedgerUserGroups> {

	protected Logger LOG = LoggerFactory.getLogger(LedgerUserGroupTransformerImpl.class);

	@Autowired
	@Qualifier("ledgerSheetServiceImpl")
	private ILedgerService<LedgerSheets> ledgerSheetServiceImpl;

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
		final List<UserDetailsTO> userDetails = new ArrayList<UserDetailsTO>();
		if (!entity.isEmpty()) {
			for (final LedgerUserGroups userGroup : entity) {
				UserDetailsTO userDetail = new UserDetailsTO();
				userDetail.setAddDisplayName(userGroup.getDisplayName());
				userDetail.setAddPersonName(userGroup.getUserName());
				userDetail.setAddComments(userGroup.getLedgerUserCommnets());
				userDetail.setUniqueSheetId(userGroup.getUserSheet().getLedgerUniqueId());
				userDetail.setLastUpdateTime(userGroup.getLastUpdateTs().toString());
				userDetail.setUserDetailId((long) userGroup.getLedgerUserId());
				userDetails.add(userDetail);
			}
		} else {
			LOG.debug("No User Details to save");
		}
		return userDetails;
	}

	@Override
	public LedgerUserGroups transformTOtoEntityForUpdate(UserDetailsTO to, LedgerUserGroups entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetailsTO transformEntityToTO(LedgerUserGroups entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerUserGroups transformTOtoEntity(final UserDetailsTO to) {
		LedgerUserGroups userGroups = new LedgerUserGroups();
		userGroups.setDisplayName(to.getAddDisplayName());
		userGroups.setUserName(to.getAddPersonName());
		userGroups.setLedgerUserCommnets(to.getAddComments());
		final LedgerSheets refSheet = ledgerSheetServiceImpl.findOne(to.getUniqueSheetId());
		userGroups.setUserSheet(refSheet);
		updateAuditColumns(userGroups);
		return userGroups;
	}

	@Override
	public UserDetailsTO transformListEntityToTO(List<LedgerUserGroups> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerUserGroups updateAuditColumns(final LedgerUserGroups userGroup) {
		userGroup.setSysActionCd("I");
		userGroup.setSysActionId("SYSTEM");
		userGroup.setSysActionNm("SYSTEM");
		return userGroup;
	}

}

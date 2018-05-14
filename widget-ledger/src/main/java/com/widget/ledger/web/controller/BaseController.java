package com.widget.ledger.web.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import com.widget.ledger.web.common.util.SecureIdGeneratorService;
import com.widget.ledger.web.domain.LedgerExpenses;
import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.exceptions.RecordNotFoundException;
import com.widget.ledger.web.service.ILedgerService;
import com.widget.ledger.web.to.DisplayTO;
import com.widget.ledger.web.to.ErrorTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

public class BaseController {

	@Autowired
	@Qualifier("ledgerSheetServiceImpl")
	public ILedgerService<LedgerSheets> ledgerSheetServiceImpl;

	@Autowired
	@Qualifier("ledgerSheetTransformerImpl")
	public IGenericTransformer<DisplayTO, LedgerSheets> ledgerSheetTransformerImpl;

	/**
	 * @return
	 */
	public ModelAndView onLoadExpenses(final String uniqueID) {
		final ModelAndView view = new ModelAndView();
		view.addObject("user", "Unknown");
		DisplayTO displayTo = new DisplayTO();
		ErrorTO errorTO = new ErrorTO();
		try {
			displayTo = getSheetDetails(uniqueID);
			view.setViewName("expense/createExpense");
		} catch (RecordNotFoundException e) {
			view.setViewName("/welcome");
			errorTO.setErrMsg(e.getMessage());
			view.addObject("errorTO", errorTO);
		}
		view.addObject("displayTO", displayTo);
		return view;
	}

	private DisplayTO getSheetDetails(final String uniqueID) {
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl.findOne(uniqueID);
		if (ObjectUtils.equals(ledgerSheet, null)) {
			throw new RecordNotFoundException("No Record Found");
		}
		return ledgerSheetTransformerImpl.transformEntityToTO(ledgerSheet);
	}

	public ModelAndView createNewSheet(final String sheetName) {
		final DisplayTO to = new DisplayTO();
		to.setUniqueSheetId(SecureIdGeneratorService.generateUID());
		to.setSheetName(sheetName);
		final LedgerSheets ledgerSheets = ledgerSheetTransformerImpl.transformTOtoEntity(to);
		final LedgerSheets ledgerSheet = ledgerSheetServiceImpl.saveOrUpdate(ledgerSheets);
		final DisplayTO displayTO = ledgerSheetTransformerImpl.transformEntityToTO(ledgerSheet);
		return addModelAndView(displayTO);
	}

	private ModelAndView addModelAndView(final DisplayTO to) {
		final ModelAndView view = new ModelAndView("expense/createExpense");
		view.addObject("user", "Unknown");
		view.addObject("displayTO", to);
		return view;
	}
	
	public void mapBreadCrums(final LedgerSheets ledgerSheet, final DisplayTO uiTO) {
		uiTO.setTotalUsers(String.valueOf(ledgerSheet.getRelatedUsers().size()));
		uiTO.setCountExpenses(String.valueOf(ledgerSheet.getRelatedExpenses().size()));
		getTotalExpenses(ledgerSheet.getRelatedExpenses(), uiTO);
		uiTO.setCreatedOn(ledgerSheet.getSysStartTs().toString());
		uiTO.setLastUpdated(ledgerSheet.getLastUpdateTs().toString());
	}
	
	/**
	 * @param ledgerExpenses
	 * @param displayTo
	 * @return
	 */
	private void getTotalExpenses(final List<LedgerExpenses> ledgerExpenses, final DisplayTO displayTo) {
		BigDecimal totalExp = BigDecimal.ZERO;
		for (final LedgerExpenses ledgerExpense : ledgerExpenses) {
			totalExp = totalExp.add(ledgerExpense.getPaidAmount());
		}
		displayTo.setTotalExpenses(totalExp.toEngineeringString());
	}

}

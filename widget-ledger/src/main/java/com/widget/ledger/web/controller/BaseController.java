package com.widget.ledger.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import com.widget.ledger.web.common.util.SecureIdGeneratorService;
import com.widget.ledger.web.domain.LedgerSheets;
import com.widget.ledger.web.service.ILedgerService;
import com.widget.ledger.web.to.DisplayTO;
import com.widget.ledger.web.transformer.IGenericTransformer;

public class BaseController {

	@Autowired
	@Qualifier("ledgerServiceImpl")
	private ILedgerService ledgerService;

	@Autowired
	@Qualifier("ledgerSheetTransformerImpl")
	private IGenericTransformer<DisplayTO, LedgerSheets> ledgerSheetTransformerImpl;

	/**
	 * @return
	 */
	public ModelAndView onLoadExpenses(final String sheetNm) {
		final ModelAndView mv = new ModelAndView("expense/createExpense");
		mv.addObject("user", "Unknown");
		mv.addObject("displayTO", getSheetDetails(sheetNm));
		return mv;
	}

	private DisplayTO getSheetDetails(final String sheetNm) {
		LedgerSheets ledgerSheet = ledgerService.getLedgerSheetByUniqueId(sheetNm);
		if (null == ledgerSheet) {
			ledgerSheet= buildLedgerSheetDetails(sheetNm);
			//ledgerSheet = ledgerService.saveOrUpdateSheet(ledgerSheet);
		}
		final DisplayTO displayTO = ledgerSheetTransformerImpl.transformEntityToTO(ledgerSheet);
		return displayTO;
	}

	private LedgerSheets buildLedgerSheetDetails(final String sheetName) {
		LedgerSheets ledgerSheet = new LedgerSheets();
		ledgerSheet.setLedgerUniqueId(SecureIdGeneratorService.generateUID());
		ledgerSheet.setSysActionCd("I");
		ledgerSheet.setSysActionNm("NEW_SHEET");
		ledgerSheet.setLedgerSheetName(sheetName);
		return ledgerSheet;
	}
}

package com.nt.service;

import java.util.List;

import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.DcSummaryReports;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;

public interface IDcMgmtService {
	
	public Integer generateCaseNo(Integer appId);
	public List<String> showAllplanNames();
	public Integer savePlanSelection(PlanSelectionInputs plan);
	public Integer saveIncomeDetails(IncomeInputs income );
	public Integer saveEducationDetails(EducationInputs education);
	public Integer saveChildrenDetails(List<ChildrenInputs> children);
	public DcSummaryReports showDcSummary(Integer caseNo);

}

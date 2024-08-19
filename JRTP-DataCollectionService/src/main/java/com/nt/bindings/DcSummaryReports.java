package com.nt.bindings;

import java.util.List;

import com.nt.entity.CitizenAppRegistrationEntity;

import lombok.Data;
@Data 
public class DcSummaryReports {
	
	
	private EducationInputs educationDetails;
	private List<ChildrenInputs> childrenDetails;
	private IncomeInputs incomeDetails;
	private CitizenAppRegistrationInputs citizenDetails;
	private String planName;
	
	

}

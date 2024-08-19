package com.nt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.bindings.ChildrenInputs;
import com.nt.bindings.CitizenAppRegistrationInputs;
import com.nt.bindings.DcSummaryReports;
import com.nt.bindings.EducationInputs;
import com.nt.bindings.IncomeInputs;
import com.nt.bindings.PlanSelectionInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.DcCaseEntity;
import com.nt.entity.DcChildrenEntity;
import com.nt.entity.DcEducationEntity;
import com.nt.entity.DcIncomeEntity;
import com.nt.entity.PlanEntity;
import com.nt.repository.IApplicationRepository;
import com.nt.repository.IChildrenRepository;
import com.nt.repository.IDcCaseRepository;
import com.nt.repository.IDcEducationRepository;
import com.nt.repository.IDcIncomeRepository;
import com.nt.repository.IPlanRepository;
@Service
public class DcMgmtServiceImpl implements IDcMgmtService {
	
	@Autowired
	private IDcCaseRepository caseRepository;
	@Autowired
	private IApplicationRepository citizenAppRepo;
	@Autowired
	private IPlanRepository planRepository; 
	@Autowired
	private IDcIncomeRepository incomeRepo;
	@Autowired
	private IDcEducationRepository educationRepo;
	@Autowired
	private IChildrenRepository childrenRepo;
	
	

	@Override
	public Integer generateCaseNo(Integer appId) {
		//load the Citizen Data
		Optional<CitizenAppRegistrationEntity> appCitizen=citizenAppRepo.findById(appId);
		if(appCitizen.isPresent()) {
			DcCaseEntity caseEntity=new DcCaseEntity();
			caseEntity.setAppId(appId);
			return caseRepository.save(caseEntity).getCaseNo();//save obj operation
			
		}
	
		return 0;
	}

	@Override
	public List<String> showAllplanNames() {
		List<PlanEntity> plansList=planRepository.findAll();
		List<String> planNamesList=plansList.stream().map(plan->plan.getPlanName()).toList();
	
		return planNamesList;
	}

	@Override
	public Integer savePlanSelection(PlanSelectionInputs plan) {
		//load DcCaseEntity object
		Optional<DcCaseEntity> opt=caseRepository.findById(plan.getCaseNo());
		if(opt.isPresent()) {
			DcCaseEntity caseEntity=opt.get();
			caseEntity.setPlanId(plan.getPlanId());
			//update the DccaseEntity with planId
			caseRepository.save(caseEntity);
			return caseEntity.getCaseNo();
		}
		
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
		//convert binding obj data to Entity class obj data
		DcIncomeEntity incomeEntity=new DcIncomeEntity();
		BeanUtils.copyProperties(income,incomeEntity);
		//save the income details
		incomeRepo.save(incomeEntity);
         return income.getCaseNo();
	}

	@Override
	public Integer saveEducationDetails(EducationInputs education) {
		DcEducationEntity educationEntity=new DcEducationEntity();
		BeanUtils.copyProperties(education,educationEntity);
	    educationRepo.save(educationEntity);
	    return education.getCaseNo();
	}

	@Override
	public Integer saveChildrenDetails(List<ChildrenInputs> children) {
		children.forEach(child->{
			DcChildrenEntity childrenEntity=new DcChildrenEntity();
			BeanUtils.copyProperties(child,childrenEntity);
			childrenRepo.save(childrenEntity);
			});
		   return children.get(0).getCaseNo();
	}

	@Override
	public DcSummaryReports showDcSummary(Integer caseNo) {
		//get multiple entity obj based on caseNo
		DcIncomeEntity incomeEntity=incomeRepo.findBycaseNo(caseNo);
		DcEducationEntity educationEntity=educationRepo.findBycaseNo(caseNo);
		List<DcChildrenEntity> childrenEntityList=childrenRepo.findByCaseNo(caseNo);
		Optional<DcCaseEntity> optcaseEntity=caseRepository.findById(caseNo);
		//get plan name
		String planName=null;
	    Integer appId=null;
		if(optcaseEntity.isPresent()) {
			DcCaseEntity caseEntity=optcaseEntity.get();
			Integer planId=caseEntity.getPlanId();
			 appId=caseEntity.getAppId();
			Optional<PlanEntity> optPlanEntity=planRepository.findById(planId);
			if(optPlanEntity.isPresent()) {
				planName=optPlanEntity.get().getPlanName();
			}
		}
		Optional<CitizenAppRegistrationEntity> optCitizenEntity=citizenAppRepo.findById(appId);
		CitizenAppRegistrationEntity citizenEntity=null;
		if(optCitizenEntity.isPresent())
			citizenEntity=optCitizenEntity.get();
		
		
		
		
		
		//convert Entity objs to Binding objs
		IncomeInputs income=new IncomeInputs();
		BeanUtils.copyProperties(incomeEntity,income);
		EducationInputs education=new EducationInputs();
		BeanUtils.copyProperties(educationEntity,education);
		List<ChildrenInputs> listchildren=new ArrayList();
		childrenEntityList.forEach(chilrenEntity->{
			ChildrenInputs child=new ChildrenInputs();
			BeanUtils.copyProperties(chilrenEntity,child);
			listchildren.add(child);
			
		});
		CitizenAppRegistrationInputs citizen=new CitizenAppRegistrationInputs();
		BeanUtils.copyProperties(citizenEntity,citizen);
		
		//prepare DcSummaryReport obj
		DcSummaryReports report=new DcSummaryReports();
		report.setPlanName(planName);
		report.setIncomeDetails(income);
		report.setEducationDetails(education);
		report.setCitizenDetails(citizen);
		report.setChildrenDetails(listchildren);
		return report;
	}

}

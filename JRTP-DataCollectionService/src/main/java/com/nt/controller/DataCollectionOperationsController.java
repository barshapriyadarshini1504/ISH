package com.nt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.PlanSelectionInputs;
import com.nt.service.IDcMgmtService;

@RestController
@RequestMapping("/dc-api")
public class DataCollectionOperationsController {
	@Autowired
	private IDcMgmtService dcService ;
	@GetMapping("/planName")
	public ResponseEntity<List<String>> displayPlanName(){
		List<String> listPlanName=dcService.showAllplanNames();
		return new ResponseEntity<List<String>>(listPlanName,HttpStatus.OK);
		
	}
	@PostMapping("/generateCaseNo/(appId)")
	public ResponseEntity<Integer> generateCaseNo(@PathVariable Integer appId){
		//use service
		Integer caseNo=dcService.generateCaseNo(appId);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
		
	}
	@PutMapping("/updateplanselection/")
	public ResponseEntity<Integer> savePlanSelection(@RequestBody PlanSelectionInputs inputs){
		//use service
		Integer caseNo=dcService.savePlanSelection(inputs);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
		
	}

}

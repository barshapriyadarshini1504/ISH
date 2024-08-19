package com.nt.service;

import org.springframework.stereotype.Component;

import com.nt.binding.CitizenAppRegistrationInputs;
import com.nt.exceptions.InvalidSSNException;
@Component
public interface ICitizenApplicationRegistrationService {
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs)throws InvalidSSNException;

}

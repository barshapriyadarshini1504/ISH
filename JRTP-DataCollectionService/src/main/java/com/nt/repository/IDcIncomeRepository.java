package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.DcIncomeEntity;



public interface IDcIncomeRepository extends JpaRepository<DcIncomeEntity,Integer > {
	public DcIncomeEntity findBycaseNo(int caseNo);


}

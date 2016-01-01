package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.entity.Company;
import com.dihaw.web.services.profile.repository.CompanyRepository;
import com.dihaw.web.services.profile.services.CompanyServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class CompanyServicesImpl implements CompanyServices{
	
//	@Autowired
//	CompanyRepository companyRepository;
	
//	@Transactional(propagation = Propagation.REQUIRED) 
//	public List<Company> findAll() {
//		return companyRepository.findAll();
//	}
	
//	@Transactional(propagation = Propagation.REQUIRED) 
//	public Company companyByUserId(long userId) {
//		
//		return companyRepository.findOne(userId);
//	}
	
}

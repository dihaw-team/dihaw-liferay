package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ListBankDTO;
import com.dihaw.web.services.profile.entity.Bank;
import com.dihaw.web.services.profile.repository.BankRepository;
import com.dihaw.web.services.profile.services.BankServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class BankServicesImpl implements BankServices{
	
	@Autowired
	BankRepository bankRepository;

	@Transactional(propagation = Propagation.REQUIRED) 
	public ListBankDTO findAll() {
		
		ListBankDTO list = new ListBankDTO();
		
		List<Bank> bankList = bankRepository.findAll();

		list.setList(bankList);
		
		return list;
	}
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public Bank bankByUserId(long userId) {
		
		return bankRepository.findOne(userId);
	}
}

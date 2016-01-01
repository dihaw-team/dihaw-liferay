package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ListAccountDTO;
import com.dihaw.web.services.profile.entity.Account;
import com.dihaw.web.services.profile.repository.AccountRepository;
import com.dihaw.web.services.profile.services.AccountServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class AccountServicesImpl implements AccountServices{

	@Autowired
	AccountRepository accountRepository;

	@Transactional(propagation = Propagation.REQUIRED) 
	public ListAccountDTO findAll() {
		
		ListAccountDTO list = new ListAccountDTO();

		List<Account> accountList = accountRepository.findAll();
		
		list.setList(accountList);
		
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public Account accountByUserId(long userId) {
		
		return accountRepository.findOne(userId);
	}
}

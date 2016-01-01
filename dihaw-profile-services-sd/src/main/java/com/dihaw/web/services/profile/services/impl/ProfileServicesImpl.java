package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ProfileDTO;
import com.dihaw.web.services.profile.entity.Account;
import com.dihaw.web.services.profile.entity.Bank;
import com.dihaw.web.services.profile.entity.Profile;
import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.repository.AccountRepository;
import com.dihaw.web.services.profile.repository.BankRepository;
import com.dihaw.web.services.profile.repository.ProfileRepository;
import com.dihaw.web.services.profile.repository.UserRepository;
import com.dihaw.web.services.profile.services.ProfileServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProfileServicesImpl implements ProfileServices{
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BankRepository bankRepository;

	@Transactional(propagation = Propagation.REQUIRED) 
	public List<Profile> findAll() {
		
		ProfileDTO profile = new ProfileDTO();
		
//		private UserDTO user;
//		private ListBankDTO listBank;
//		private String name;
//		private String description;
		

		
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public ProfileDTO profileByUserId(long userId) {
		
		ProfileDTO profile = new ProfileDTO();
		
//		List<Profile> 	listProfile 	= profileRepository.findAll();
		List<Bank> 		listBank 		= bankRepository.findDistinctBankByUserId(userId);
		List<Account> 	listAccount 	= accountRepository.findByUserId(userId);
		User 			user 			= userRepository.findOne(userId);
		
		System.out.println("-------------");
		
		profile.setUser(user);
		profile.setBanks(listBank);
		profile.setAccounts(listAccount);
		profile.setName("name");
		profile.setDescription("description");
		
		
//		 profileRepository.findProfilesByUserId(userId);
		
		return profile;
	}


}

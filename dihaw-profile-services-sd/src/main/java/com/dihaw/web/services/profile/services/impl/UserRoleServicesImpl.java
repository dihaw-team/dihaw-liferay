package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ListUserRoleDTO;
import com.dihaw.web.services.profile.entity.UserRole;
import com.dihaw.web.services.profile.repository.UserRoleRepository;
import com.dihaw.web.services.profile.services.UserRoleServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class UserRoleServicesImpl implements UserRoleServices{
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Transactional(propagation = Propagation.REQUIRED) 
	public ListUserRoleDTO findAll() {
		
		ListUserRoleDTO list = new ListUserRoleDTO();
		
		List<UserRole> userRoleList = userRoleRepository.findAll();

		list.setList(userRoleList);
		
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public List<String> userRoleByUserId(long userId) {
		
		return userRoleRepository.findByUserId(userId);
	}

}

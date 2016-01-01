package com.dihaw.web.services.profile.services;

import java.util.List;

import com.dihaw.web.services.profile.dto.ListUserRoleDTO;

public interface UserRoleServices {
	

	ListUserRoleDTO findAll();
	
	List<String> userRoleByUserId(long userId);

}

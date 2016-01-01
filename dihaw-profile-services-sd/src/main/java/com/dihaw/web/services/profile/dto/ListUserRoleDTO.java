package com.dihaw.web.services.profile.dto;

import java.util.List;

import com.dihaw.web.services.profile.entity.UserRole;

public class ListUserRoleDTO {
	
	List<UserRole> list;

	public List<UserRole> getList() {
		return list;
	}

	public void setList(List<UserRole> list) {
		this.list = list;
	}
	

}

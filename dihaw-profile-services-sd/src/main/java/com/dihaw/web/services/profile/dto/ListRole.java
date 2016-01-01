package com.dihaw.web.services.profile.dto;

import java.util.List;

import com.dihaw.web.services.profile.entity.Role;

public class ListRole {

	List<Role> list;
	
	public ListRole(){
		
	}

	public List<Role> getList() {
		return list;
	}

	public void setList(List<Role> list) {
		this.list = list;
	}
	
	
}

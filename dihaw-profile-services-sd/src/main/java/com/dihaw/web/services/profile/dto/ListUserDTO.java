package com.dihaw.web.services.profile.dto;

import java.util.List;

import com.dihaw.web.services.profile.entity.User;

public class ListUserDTO {
	
	List<User> list;

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

}

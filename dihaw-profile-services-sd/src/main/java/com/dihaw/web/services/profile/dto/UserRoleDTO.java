package com.dihaw.web.services.profile.dto;

import com.dihaw.web.services.profile.entity.Role;

public class UserRoleDTO {
	
	private long userId;
	private Role role;
	
	private UserRoleDTO(){
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}

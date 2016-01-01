package com.dihaw.web.services.profile.dto;

import java.util.Date;

import com.dihaw.web.services.profile.entity.UserStatus;

public class UserDTO {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private Date lastConnection;
	private Date creationDate;
	private UserStatus status;
	private int accountNonExpired;
	private int accountNonLocked;
	private int credentialsNonExpired;
	private AddressDTO address;
	
}

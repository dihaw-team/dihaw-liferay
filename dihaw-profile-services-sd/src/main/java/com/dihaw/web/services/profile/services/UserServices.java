package com.dihaw.web.services.profile.services;

import com.dihaw.web.services.profile.dto.ListUserDTO;
import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.exception.UserNotFoundException;

public interface UserServices {

	public void updateUser(User user) throws UserNotFoundException;
	
	public void updateLastConnection( String username) throws UserNotFoundException;
	
	public User getUserByUsername(String username);

	public void changeAccountExpired(String id, String value) throws UserNotFoundException;

	public void changeAccountLocked(String id, String value) throws UserNotFoundException;

	public void changeCredentialsExpired(String id, String value) throws UserNotFoundException;

	public ListUserDTO findAll();

	public User userByUserId(long userId);

}

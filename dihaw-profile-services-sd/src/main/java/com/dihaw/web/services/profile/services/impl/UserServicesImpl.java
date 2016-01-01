package com.dihaw.web.services.profile.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ListUserDTO;
import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.exception.UserNotFoundException;
import com.dihaw.web.services.profile.repository.RoleRepository;
import com.dihaw.web.services.profile.repository.UserRepository;
import com.dihaw.web.services.profile.services.UserServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class UserServicesImpl implements UserServices{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	UserRepository userRepository;
	 
	@Autowired 
	RoleRepository roleRepository;
	 
	@Transactional(propagation = Propagation.REQUIRED) 
	public void updateUser(User user) throws UserNotFoundException {
		 
		User userSearch = null;
//				userRepository.findOne(user.getId());
		 
		if(userSearch == null)
			throw new UserNotFoundException(String.format("No user found for id "+user.getId()));
		 
//		userRepository.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),  
//				user.getEmail(), user.getPassword());
		 
	} 

	@Transactional(propagation = Propagation.REQUIRED) 
	public void updateLastConnection(String username)
			throws UserNotFoundException { 
			 
		 Date date = new Date();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date today = Calendar.getInstance().getTime();
		 String reportDate = df.format(today);
		  
		 try { 
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reportDate);
		} catch (ParseException e) {
			logger.info("-----ParseException: "+e.getMessage());
		} 
		 
		userRepository.updateLastConnection(username, date);
		 
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public User getUserByUsername(String username) {
		 
		return userRepository.findByUsername(username);
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public void changeAccountExpired(String id, String value)
			throws UserNotFoundException { 
		value = (value.equals("1") ) ? "0" : "1";
		 
		userRepository.changeAccountExpired(Integer.parseInt(id), Integer.parseInt(value));
		 
	} 
 
 
	@Transactional(propagation = Propagation.REQUIRED) 
	public void changeAccountLocked(String id, String value)
			throws UserNotFoundException { 
		value = (value.equals("1") ) ? "0" : "1";
		 
		userRepository.changeAccountLocked(Integer.parseInt(id), Integer.parseInt(value));
		 
	} 
 
 
	@Transactional(propagation = Propagation.REQUIRED) 
	public void changeCredentialsExpired(String id, String value)
			throws UserNotFoundException { 
		value = (value.equals("1") ) ? "0" : "1";
		 
		userRepository.changeCredentialsExpired(Integer.parseInt(id), Integer.parseInt(value));
		 
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public ListUserDTO findAll() {
		
		ListUserDTO list = new ListUserDTO();
		
		List<User> userList = userRepository.findAll();
		
		System.out.println("-----------userList.size(): "+userList.size());
		if(userList.size()>0){
			for(int i=0; i< userList.size() ; ){
				System.out.println("------userList.get("+i+").getAccounts().size(): "+userList.get(i).getAccounts().size());
				
				
				
				i = i + 1;
				
				
			}
		}
		
		list.setList(userList);
		
		return list;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public User userByUserId(long userId) {
		
		return userRepository.findOne(userId);
	} 

}

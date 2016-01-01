package com.dihaw.web.services.profile.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ListUserDTO;
import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.services.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserServices userServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ListUserDTO getAll() {

    	System.out.println("UserController------------get all");
    	
    	return userServices.findAll();
    }
	
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User userByUserId(@PathVariable("userId") long userId) {

    	System.out.println("UserController------------userByUserId");
    	
    	return userServices.userByUserId(userId);
    }
}

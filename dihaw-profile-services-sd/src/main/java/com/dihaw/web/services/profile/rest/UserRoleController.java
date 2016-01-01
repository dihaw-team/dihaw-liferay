package com.dihaw.web.services.profile.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ListUserRoleDTO;
import com.dihaw.web.services.profile.services.UserRoleServices;

@Controller
@RequestMapping("/userRole")
public class UserRoleController {
	
	@Autowired
	UserRoleServices userRoleServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ListUserRoleDTO getAll() {

    	System.out.println("userRoleServices------------get all");
    	
    	return userRoleServices.findAll();
    }
    
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public List<String> userRoleByUserId(@PathVariable("userId") long userId) {

    	System.out.println("userRoleServices------------userRoleByUserId");
    	
    	return userRoleServices.userRoleByUserId(userId);
    }

}

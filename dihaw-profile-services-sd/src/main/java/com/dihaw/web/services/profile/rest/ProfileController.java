package com.dihaw.web.services.profile.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ProfileDTO;
import com.dihaw.web.services.profile.entity.Profile;
import com.dihaw.web.services.profile.services.ProfileServices;


@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	ProfileServices profileServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Profile> getAll() {

    	System.out.println("ProfileServices------------get all");
    	
    	return profileServices.findAll();
    }
    
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ProfileDTO profileByUserId(@PathVariable("userId") long userId) {

    	System.out.println("ProfileServices------------profileByUserId");
    	
    	return profileServices.profileByUserId(userId);
    }

}

package com.dihaw.web.services.profile.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.schemas.profile.ChangePasswordAndLoginRequest;
import com.dihaw.web.schemas.profile.ChangePasswordAndLoginResponse;
import com.dihaw.web.schemas.profile.ChangePasswordRequest;
import com.dihaw.web.schemas.profile.ChangePasswordResponse;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;
import com.dihaw.web.services.profile.services.LoginServices;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginServices loginServices;

    @ResponseBody
    @RequestMapping(value = "/performLogin", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) {


    	logger.trace("Delegating request {} to service.", request);
    	
    	return loginServices.login(request);
    }

    @ResponseBody
    @RequestMapping(value = "/getFirst", method = RequestMethod.GET)
    public LoginResponse getFirst() {

    	System.out.println("-----------------get first");

    	
    	return loginServices.getFirst();
    }
    
    @ResponseBody
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request) {
	logger.trace("Delegating request {} to service.", request);

	return loginServices.changePassword(request);
    }

    @ResponseBody
    @RequestMapping(value = "/changePasswordAndLogin", method = RequestMethod.POST)
    public ChangePasswordAndLoginResponse changePasswordAndLogin(@RequestBody ChangePasswordAndLoginRequest request) {
	logger.trace("Delegating request {} to service.", request);

	return loginServices.changePasswordAndLogin(request);
    }

}

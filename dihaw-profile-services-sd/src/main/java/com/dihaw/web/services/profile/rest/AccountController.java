package com.dihaw.web.services.profile.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ListAccountDTO;
import com.dihaw.web.services.profile.entity.Account;
import com.dihaw.web.services.profile.services.AccountServices;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountServices accountServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ListAccountDTO getAll() {

    	System.out.println("AccountController------------get all");
    	
    	return accountServices.findAll();
    }
    
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Account accountByUserId(@PathVariable("userId") long userId) {

    	System.out.println("AccountServices------------accountByUserId");
    	
    	return accountServices.accountByUserId(userId);
    }

}

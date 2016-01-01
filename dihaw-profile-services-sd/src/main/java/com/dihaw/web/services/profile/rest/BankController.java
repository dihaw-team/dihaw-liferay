package com.dihaw.web.services.profile.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ListBankDTO;
import com.dihaw.web.services.profile.entity.Bank;
import com.dihaw.web.services.profile.services.BankServices;

@Controller
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	BankServices bankServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ListBankDTO getAll() {

    	System.out.println("BankController------------get all");
    	
    	return bankServices.findAll();
    }
    
	@ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Bank bankByUserId(@PathVariable("userId") long userId) {

    	System.out.println("BankController------------bankByUserId");
    	
    	return bankServices.bankByUserId(userId);
    }

}

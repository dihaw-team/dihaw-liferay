package com.dihaw.web.services.profile.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.dto.ListAddressDTO;
import com.dihaw.web.services.profile.services.AddressServices;

@Controller
public class AddressController {
	
	@Autowired
	AddressServices addressServices;
	
	@ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ListAddressDTO getAll() {

    	System.out.println("AddressController------------get all");
    	
    	return addressServices.findAll();
    }

}

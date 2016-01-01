package com.dihaw.web.services.profile.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dihaw.web.services.profile.entity.Company;
import com.dihaw.web.services.profile.services.CompanyServices;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
//	@Autowired
//	CompanyServices companyServices;
	
//	@ResponseBody
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public List<Company> getAll() {
//
//    	System.out.println("CompanyController------------get all");
//    	
//    	return companyServices.findAll();
//    }
//    
//    @ResponseBody
//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    public Company conmpanyByUserId(@PathVariable("userId") long userId) {
//
//    	System.out.println("CompanyController------------conmpanyByUserId");
//    	
//    	return companyServices.companyByUserId(userId);
//    }

}

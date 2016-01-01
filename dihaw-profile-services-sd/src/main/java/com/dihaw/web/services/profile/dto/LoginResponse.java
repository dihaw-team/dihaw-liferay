package com.dihaw.web.services.profile.dto;

import java.io.Serializable;

import com.dihaw.web.schemas.profile.LoginStatusType;
import com.dihaw.web.schemas.profile.UserIdentificationType;

public class LoginResponse  implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected LoginStatusType loginStatus;
    protected UserIdentificationType id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String status;
    
    
    public LoginResponse(){
    	
    }

    public LoginResponse(String firstName, String lastName,
    		String emailAddress, String userStatus){
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = emailAddress;
    	this.email = userStatus;
    }

	
    
    
    
}

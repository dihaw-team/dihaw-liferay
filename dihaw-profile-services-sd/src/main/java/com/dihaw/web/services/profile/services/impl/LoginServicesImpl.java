package com.dihaw.web.services.profile.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.DefaultExceptionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.schemas.profile.ChangePasswordAndLoginRequest;
import com.dihaw.web.schemas.profile.ChangePasswordAndLoginResponse;
import com.dihaw.web.schemas.profile.ChangePasswordRequest;
import com.dihaw.web.schemas.profile.ChangePasswordResponse;
import com.dihaw.web.schemas.profile.FraudDetectionLogRequest;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;
import com.dihaw.web.schemas.profile.LoginStatusType;
import com.dihaw.web.schemas.profile.RoleType;
import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.entity.UserStatus;
import com.dihaw.web.services.profile.repository.LoginRepository;
import com.dihaw.web.services.profile.services.LoginServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class LoginServicesImpl implements LoginServices{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final RoleType COMMON_USER;
	
	@Autowired LoginRepository loginRepository;

	static {
		COMMON_USER = new RoleType();
		COMMON_USER.setId(-2l);
		COMMON_USER.setName("ROLE_COMMON_USER");
    }
    
    private static final RoleType SUPER_USER;
    
    static {
    	SUPER_USER = new RoleType();
    	SUPER_USER.setId(-1l);
    	SUPER_USER.setName("ROLE_SUPER_USER");
    }	

    @Transactional(propagation = Propagation.REQUIRED) 
	public LoginResponse getFirst() {
    	
    	LoginResponse loginResponse = new LoginResponse();
    	
    	User user = loginRepository.getFirst();
    	
    	if(user!= null){
//    		loginResponse.setEmailAddress(user.getEmail());
//    		loginResponse.setFirstName(user.getFirstName());
//    		loginResponse.setLastName(user.getLastName());
//    		loginResponse.setUserStatus(user.getStatus().value());
    	}
    	
		return loginResponse;
	}
	
	
    @Transactional(propagation = Propagation.REQUIRED) 
    public LoginResponse login(LoginRequest request) {
    	
    	System.out.println("--LoginResponse login-----------0-request.emailAddress: "+request.getEmailAddress());
    	System.out.println("--LoginResponse login-----------0-request.password: "+request.getPassword());
    	
		request.setPassword(encrypt(request.getPassword()));
		
		System.out.println("--LoginResponse login-----------1-request.emailAddress: "+request.getEmailAddress());
		System.out.println("--LoginResponse login-----------1-request.password: "+request.getPassword());
			
		logger.info("Invoking stored procedure for login.");
	
		User user = loginRepository.performLogin(request.getEmailAddress(), request.getPassword());	
		
		System.out.println("--LoginResponse user------------user: "+user);
		
		LoginResponse loginResponse = new LoginResponse();
		
		if (user == null) {
			
			System.out.println("--LoginResponse login------------response == null");
			
			loginResponse.setLoginStatus(LoginStatusType.NOT_FOUND);
		} else {
			
//	  		loginResponse.setEmailAddress(user.getEmail());
    		loginResponse.setFirstName(user.getFirstName());
    		loginResponse.setLastName(user.getLastName());
//    		loginResponse.setUserStatus(user.getStatus().value());
			
			System.out.println("--LoginResponse user------------user.firstName: "+user.getFirstName());
			
		    if(user.getStatus().equals(UserStatus.WAITING_FIRST_LOGIN)){
		    	loginResponse.setLoginStatus(LoginStatusType.FIRST_LOGIN);
		    }
		    else if(user.getStatus().equals(UserStatus.DISABLED)
		    		|| 	user.getStatus().equals(UserStatus.BLOKED)){
		    	loginResponse.setLoginStatus(LoginStatusType.ACCESS_DENIED);
		    }
		    else if(user.getStatus().equals(UserStatus.ENABLED)){
		    	loginResponse.setLoginStatus(LoginStatusType.SUCCESS);
		    }
		    
		    System.out.println("--LoginResponse user------------loginResponse.loginStatus: "+loginResponse.getLoginStatus());
		    
		}
		
		
//		LoginStatusType status = loginResponse.getLoginStatus();
		
//		System.out.println("--LoginResponse login------------status: "+status.value());
		
		if (loginResponse.getLoginStatus() == LoginStatusType.SUCCESS || loginResponse.getLoginStatus() == LoginStatusType.FIRST_LOGIN
			|| loginResponse.getLoginStatus() == LoginStatusType.PASSWORD_EXPIRED) {
			
			
			System.out.println("--LoginResponse login------------20");
							
			// Block login for user connected from unknown host
	//		performIpCheckAccess(request, response);
			if (loginResponse.getLoginStatus() == LoginStatusType.ACCESS_DENIED) {
				
				System.out.println("--LoginResponse login------------21");
				return loginResponse;
			}
			
			System.out.println("--LoginResponse login------------22");
		    logger.debug("Login successfully performed. Loading user profile.");
		    
		} else if( loginResponse.getLoginStatus() == LoginStatusType.WRONG_PASSWORD 
				|| loginResponse.getLoginStatus() == LoginStatusType.USER_BLOCKED) {
			
			System.out.println("--LoginResponse login------------23");
	 
		    /* DTO Richiesta per Fraud detection */
	        FraudDetectionLogRequest fraudDetectionRequest = new FraudDetectionLogRequest();
	        fraudDetectionRequest.setUserId(request.getEmailAddress());
	        
	        System.out.println("--LoginResponse login------------24");
	        
		    if(loginResponse.getLoginStatus() == LoginStatusType.WRONG_PASSWORD) {
		    	
		    	System.out.println("--LoginResponse login------------25");
		    	
		        fraudDetectionRequest.setResultCode(6);
		        fraudDetectionRequest.setOperationDescription("Accesso KO. Password errata.");
		        
		    } else if(loginResponse.getLoginStatus() == LoginStatusType.USER_BLOCKED) {
		    	
		    	System.out.println("--LoginResponse login------------26");
		    	
		        fraudDetectionRequest.setResultCode(3);
		        fraudDetectionRequest.setOperationDescription("Accesso KO. Password bloccata.");	        
		    }
		    
		    System.out.println("--LoginResponse login------------27");
		    
//	        fraudDetectionLog.fraudDetectionLog(fraudDetectionRequest);
	        
		}
		
		
		System.out.println("--LoginResponse login------------28");
		
		return loginResponse;
    }
	
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	public ChangePasswordAndLoginResponse changePasswordAndLogin(
			ChangePasswordAndLoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected String encrypt(String plainPassword) {
		
		logger.info("Retrieving encryption algorithm.");
		
		String algorithm = "SHA-1";
		
		try {
			
			MessageDigest md = MessageDigest.getInstance(algorithm);
		
			byte[] encrypted = md.digest(plainPassword.getBytes());
			
			return Hex.encodeHexString(encrypted);
			
		} catch (NoSuchAlgorithmException nsae) {
		    throw new ContextedRuntimeException("Error while trying to retrieve algorithm.", nsae,
				    new DefaultExceptionContext().addContextValue("algorithm", algorithm));
		    }

		}

}

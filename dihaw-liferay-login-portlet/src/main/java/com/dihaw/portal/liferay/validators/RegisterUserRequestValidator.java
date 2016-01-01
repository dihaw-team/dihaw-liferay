package com.dihaw.portal.liferay.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dihaw.portal.utils.ValidationSupportUtils;
import com.dihaw.web.schemas.profile.RegisterUserRequest;

@Component("registerUserRequestValidator")
public class RegisterUserRequestValidator implements Validator {
	
	private final static int MAX_LENGTH = 10;
	private final static int MIN_LENGTH = 3;
	
    public boolean supports(Class<?> clazz) {
	return RegisterUserRequestValidator.class.isAssignableFrom(clazz);
    }

    public void validate(Object arg0, Errors errors) {
    	
    	RegisterUserRequest target = (RegisterUserRequest) arg0;
    	
    	ValidationSupportUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field-required");
    	ValidationSupportUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field-required");
    	
		if (!errors.hasFieldErrors("firstName") && target.getFirstName() != null) {
			if (target.getFirstName().length() > MAX_LENGTH || target.getFirstName().length() < MIN_LENGTH) {
				errors.rejectValue("firstName", "validation.firstName.bad.length", 
						new Object[]{MIN_LENGTH, MAX_LENGTH}, "validation.firstName.bad.length");
			}
			
			if (!errors.hasFieldErrors("firstName")) {
				ValidationSupportUtils.rejectIfContainsSpecialChars(errors, "firstName", "validation.bad.chars");
			}
			
			if (!errors.hasFieldErrors("lastName")) {
				ValidationSupportUtils.rejectIfContainsSpecialChars(errors, "lastName", "validation.bad.chars");
			}
			
		}
    }
    
}

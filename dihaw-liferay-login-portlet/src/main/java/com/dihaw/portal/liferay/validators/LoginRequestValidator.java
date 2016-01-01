package com.dihaw.portal.liferay.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dihaw.web.schemas.profile.LoginRequest;

@Component("loginRequestValidator")
public class LoginRequestValidator implements Validator {
    public boolean supports(Class<?> clazz) {
	return LoginRequest.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "field-required");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field-required");
    }
}

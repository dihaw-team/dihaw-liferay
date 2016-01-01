package com.dihaw.portal.liferay.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dihaw.web.schemas.profile.ChangePasswordRequest;

@Component("changePasswordRequestValidator")
public class ChangePasswordRequestValidator implements Validator {
    public boolean supports(Class<?> clazz) {
	return ChangePasswordRequest.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
	ChangePasswordRequest req = (ChangePasswordRequest) target;

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "field-required");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "field-required");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", "field-required");

	if (StringUtils.equals(req.getOldPassword(), req.getNewPassword())) {
	    errors.rejectValue("newPassword", "equals-to-old");
	}
	if (!StringUtils.equals(req.getNewPassword(), req.getRepeatPassword())) {
	    errors.rejectValue("repeatPassword", "do-not-match");
	}
	if (!PasswordValidationUtils.isValidPassword(req.getNewPassword())) {
	    errors.rejectValue("newPassword", "wrong-new-password");
	}
    }
}

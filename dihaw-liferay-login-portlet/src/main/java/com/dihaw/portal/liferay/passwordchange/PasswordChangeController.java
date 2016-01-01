package com.dihaw.portal.liferay.passwordchange;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.portal.liferay.LoginUtils;
import com.dihaw.portal.liferay.login.LoginController;
import com.dihaw.web.schemas.profile.ChangePasswordRequest;
import com.dihaw.web.schemas.profile.ChangePasswordResponse;
import com.dihaw.web.schemas.profile.ChangePasswordStatusType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * Controller used to manage the password change for the currently logged in user.
 * 
 */
@Controller
@SessionAttributes(LoginController.LOGIN_RESULT)
@RequestMapping("VIEW")
public class PasswordChangeController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String CHANGE_PASSWORD_MODEL = "changePassword";
    
    private String changePasswordURL = "http://localhost:8088/dihaw-profile-services-sd/rest/login/changePassword";

    @Autowired
    @Qualifier("changePasswordRequestValidator")
    private Validator validator;
    @Autowired
    private RestTemplate restTemplate;

    @InitBinder("changePasswordRequestValidator")
    public void initBinder(PortletRequestDataBinder binder) {
	binder.setValidator(validator);
    }

    @ModelAttribute(CHANGE_PASSWORD_MODEL)
    public ChangePasswordRequest getChangePassword() {
	return new ChangePasswordRequest();
    }

    @RenderMapping(params = "!display")
    public String showChangePasswordForm() {
	return "change-password";
    }

    @ActionMapping("doChangePassword")
    public void doChangePassword(
	    @Valid @ModelAttribute(CHANGE_PASSWORD_MODEL) ChangePasswordRequest changePasswordRequest,
	    BindingResult bindingResult, ActionRequest request, ActionResponse response) throws IOException,
	    PortalException, SystemException {
	logger.info("Performing password change.");

	if (!bindingResult.hasErrors()) {
	    logger.debug("No formal errors found on form. Proceeding with password change.");

	    changePasswordRequest.setUserId(LoginUtils.getCurrentUserId());

	    logger.debug("Trying to change the password for request {}.", changePasswordRequest);

	    ChangePasswordResponse changePasswordResponse = restTemplate.postForObject(
	    		changePasswordURL, changePasswordRequest, ChangePasswordResponse.class);

	    ChangePasswordStatusType status = changePasswordResponse.getStatus();
	    switch (status) {
	    case SUCCESS:
		logger.info("Change password successful. User already logged in, performing portal login.");

		response.setRenderParameter("display", "success");
		break;
	    case WRONG_PASSWORD:
		bindingResult.rejectValue("oldPassword", "wrong-password");
		break;
	    case WRONG_NEW_PASSWORD:
		bindingResult.rejectValue("newPassword", "wrong-new-password");
		break;
	    case WRONG_REPEAT_PASSWORD:
		bindingResult.rejectValue("repeatPassword", "do-not-match");
		break;
	    default:
		logger.warn("Default behavior detected. Unsupported ChangePasswordStatus intercepted: {}.", status);

		bindingResult.reject("authentication-failure");
	    }
	}

    }

    @RenderMapping(params = "display=success")
    public String showSuccess() {
    	return "password-change-success";
    }
}

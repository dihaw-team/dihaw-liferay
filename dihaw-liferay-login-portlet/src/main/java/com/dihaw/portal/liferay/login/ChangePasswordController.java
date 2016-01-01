package com.dihaw.portal.liferay.login;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.web.schemas.profile.ChangePasswordAndLoginRequest;
import com.dihaw.web.schemas.profile.ChangePasswordAndLoginResponse;
import com.dihaw.web.schemas.profile.ChangePasswordStatusType;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;


@Controller
@RequestMapping(value = "VIEW", params = "state=change-password")
public class ChangePasswordController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String PASSWORD_CHANGE_MODEL = "passwordChange";
    
    private String changePasswordAndLoginURL = "http://localhost:8088/dihaw-profile-services-sd/rest/login/changePasswordAndLogin";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier("changePasswordAndLoginRequestValidator")
    private Validator validator;
    @Autowired
    private Mapper mapper;

    @InitBinder
    public void initBinder(PortletRequestDataBinder binder) {
	binder.setValidator(validator);
    }
    
    @ModelAttribute(PASSWORD_CHANGE_MODEL)
    public ChangePasswordAndLoginRequest getChangePassword(PortletSession session) {
	ChangePasswordAndLoginRequest changePasswordAndLoginRequest = new ChangePasswordAndLoginRequest();

	LoginRequest loginRequest = (LoginRequest) session.getAttribute(LoginController.LOGIN_MODEL);
	mapper.map(loginRequest, changePasswordAndLoginRequest);

	return changePasswordAndLoginRequest;
    }

    @RenderMapping(params = "display=warning")
    public String showExpiringMessage(Model model, PortletSession session) {
	model.addAttribute(LoginController.EXPIRING_DAYS, session.getAttribute(LoginController.EXPIRING_DAYS));

	return "password-expiring";
    }

    @RenderMapping(params = "!display")
    public String showChangePasswordForm() {
	return "password-change";
    }

    @ActionMapping("doChangePassword")
    public void doChangePassword(
	    @Valid @ModelAttribute(PASSWORD_CHANGE_MODEL) ChangePasswordAndLoginRequest changePasswordRequest,
	    BindingResult bindingResult, ActionRequest request, ActionResponse response, PortletSession session) {
	logger.info("Performing password change.");

	if (!bindingResult.hasErrors()) {
	    logger.debug("No formal errors found on form. Proceeding with password change.");

	    LoginRequest loginRequest = (LoginRequest) session.getAttribute(LoginController.LOGIN_MODEL);
	    LoginResponse loginResponse = (LoginResponse) session.getAttribute(LoginController.LOGIN_RESULT);

	    mapper.map(loginResponse, changePasswordRequest);
	    mapper.map(loginRequest, changePasswordRequest);

	    logger.debug("Trying to change the password for request {}.", changePasswordRequest);
	    
//	    ChangePasswordAndLoginResponse changePasswordResponse = restTemplate.postForObject(
//			    Preferences.getRemoteChangePasswordAndLoginURL(), changePasswordRequest,
//			    ChangePasswordAndLoginResponse.class);
	    
	    ChangePasswordAndLoginResponse changePasswordResponse = restTemplate.postForObject(
			    changePasswordAndLoginURL, changePasswordRequest,
			    ChangePasswordAndLoginResponse.class);

	    ChangePasswordStatusType status = changePasswordResponse.getChangeStatus();
	    switch (status) {
	    case SUCCESS:
		logger.info("Change password successful. User already logged in, performing portal login.");

		response.setRenderParameter("display", "success");

		break;
	    case WRONG_PASSWORD:
		logger.warn("Wrong status detected: {}.", status);

		bindingResult.reject("authentication-failure");
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

	response.setRenderParameter("state", "change-password");
    }

    @RenderMapping(params = "display=success")
    public String showSuccess() {
	return "password-change-success";
    }
}

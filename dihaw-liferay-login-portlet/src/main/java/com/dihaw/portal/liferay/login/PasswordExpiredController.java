package com.dihaw.portal.liferay.login;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.web.schemas.profile.ChangePasswordAndLoginRequest;
import com.dihaw.web.schemas.profile.ChangePasswordAndLoginResponse;
import com.dihaw.web.schemas.profile.ChangePasswordStatusType;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginStatusType;
import com.liferay.portal.kernel.exception.SystemException;


@Controller
@SessionAttributes(LoginController.LOGIN_RESULT)
@RequestMapping(value = "VIEW", params = "state=password-expired")
public class PasswordExpiredController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String PASSWORD_CHANGE_MODEL = "passwordChange";
    public static final String LOGIN_RESULT = "loginResponse";
    
    private String changePasswordAndLoginURL = "http://localhost:8088/dihaw-profile-services-sd/rest/login/changePasswordAndLogin";

    @Autowired
    @Qualifier("changePasswordAndLoginRequestValidator")
    private Validator validator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Mapper mapper;

    @InitBinder("changePasswordAndLoginRequestValidator")
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

    @RenderMapping(params = "display=first-login")
    public String showFirstLoginMessage() {
    	
	return "first-login";
    }

    @RenderMapping(params = "display=warning")
    public String showWarningMessage() {
    	
    	return "password-expired";
    }

    @RenderMapping(params = "!display")
    public String showPasswordChangeForm() {
    	return "password-change";
    }
    
    @RenderMapping(params = "action=password-change")
    public String showPasswordChangeView() {
    	return "password-change";
    }

    @ActionMapping("goToChangePassword")
    public void doChangePassword(Model model,
	    @Valid @ModelAttribute(PASSWORD_CHANGE_MODEL) ChangePasswordAndLoginRequest changePasswordAndLoginRequest,
	    BindingResult bindingResult, ActionRequest request, ActionResponse response) 
	    		throws SystemException, IOException {
    	
	logger.info("Performing password change.");

	if (!bindingResult.hasErrors()) {
		
	    logger.debug("No formal errors found on form. Proceeding with password change.");
	    
	    ChangePasswordAndLoginResponse changePasswordAndLoginResponse = restTemplate.postForObject(
			    changePasswordAndLoginURL, changePasswordAndLoginRequest,
			    ChangePasswordAndLoginResponse.class);

	    LoginStatusType loginStatus = changePasswordAndLoginResponse.getLoginStatus();
	    if (loginStatus != LoginStatusType.SUCCESS) {
		logger.error("Unable to login the user for the request {}.", changePasswordAndLoginRequest);

		bindingResult.rejectValue("newPassword", "authentication-failure");
	    } else {
			ChangePasswordStatusType status = changePasswordAndLoginResponse.getChangeStatus();
			switch (status) {
			case SUCCESS:
			    logger.info("Change password successful. User already logged in, performing portal login.");
			    model.addAttribute("status", "CHANGE_SUCCESS");
			    response.setRenderParameter("state", "password-expired");
			    response.setRenderParameter("display", "success");

			    break;
			case WRONG_PASSWORD:
				model.addAttribute("status", "CHANGE_WRONG_PASSWORD");
			    logger.warn("Wrong status detected: {}.", status);
			    
			    response.setRenderParameter("state", "change-password");
			    response.setRenderParameter("action", "password-change");
	
			    break;
			case WRONG_NEW_PASSWORD:
				model.addAttribute("status", "CHANGE_WRONG_NEW_PASSWORD");
				
				response.setRenderParameter("state", "change-password");
				response.setRenderParameter("action", "password-change");
			    break;
			case WRONG_REPEAT_PASSWORD:
				model.addAttribute("status", "CHANGE_WRONG_REPEAT_PASSWORD");
				
				response.setRenderParameter("state", "change-password");
				response.setRenderParameter("action", "password-change");
			    break;
			default:
			    logger.warn("Default behavior detected. Unsupported ChangePasswordStatus intercepted: {}.", status);
			    
			    bindingResult.reject("authentication-failure");
			}
	    }	    
	    
		} else {
			
			response.setRenderParameter("state", "password-expired");
		}
    }

    @RenderMapping(params = "display=success")
    public String showSuccess() {
    	
	return "password-change-success";
    }
}

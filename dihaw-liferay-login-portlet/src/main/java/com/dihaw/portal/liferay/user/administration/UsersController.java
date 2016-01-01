package com.dihaw.portal.liferay.user.administration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.portal.liferay.validators.RegisterUserRequestValidator;
import com.dihaw.web.schemas.profile.ChangeStatusRequest;
import com.dihaw.web.schemas.profile.ChangeStatusResponse;
import com.dihaw.web.schemas.profile.RegisterUserRequest;
import com.dihaw.web.schemas.profile.RegisterUserResponse;
import com.dihaw.web.schemas.profile.RegisterUserStatusType;
import com.dihaw.web.schemas.profile.UserIdentificationType;
import com.dihaw.web.schemas.profile.UsersResponse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

@Controller
@RequestMapping("VIEW")
//@SessionAttributes({UsersController.PAGE, UsersController.SIZE} )
public class UsersController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    public static final String REGISTER_USER_MODEL = "registerUser";
    
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String ORDER = "order";
    
    public static final String CHANGE_STATUS_MODEL ="changeStatusRequest";
    
	private String registerUserURL = "http://localhost:8088/dihaw-profile-services-sd/rest/users/registerUser";
	private String usersListURL = "http://localhost:8088/dihaw-profile-services-sd/rest/users/list?page={page}&size={size}&order={order}";
	
	private String userChangeStatusURL = "http://localhost:8088/dihaw-profile-services-sd/rest/users/status";
	
    @Autowired
    private RestTemplate restTemplate;
    
	@Autowired
	RegisterUserRequestValidator registerUserRequestValidator;
    
    @ModelAttribute(REGISTER_USER_MODEL)
    public RegisterUserRequest getChangePassword(PortletSession session) {
    	RegisterUserRequest registerUserRequest = new RegisterUserRequest();
	return registerUserRequest;
    }

    @RenderMapping
    public String showView(Model model, RenderRequest request,
            @RequestParam(value = PAGE, required = false, defaultValue = "0") int page,
            @RequestParam(value = SIZE, required = false, defaultValue = "10") int size,
            @RequestParam(value = ORDER, required = false, defaultValue = "asc") String order) 
            		throws SystemException, PortalException {
    	logger.info("Showing Register view");
    	
    	UsersResponse usersResponse = restTemplate.getForObject(usersListURL, UsersResponse.class, page, size, order);
    	
    	int totalPages = usersResponse.getTotalPages();
    	
    	model.addAttribute(PAGE, page);
    	model.addAttribute(SIZE, size);
    	model.addAttribute(ORDER, order);
    	model.addAttribute("totalPages", totalPages);
    	model.addAttribute("users", usersResponse.getUsers());
    	
    	return "view";
    }
    
	@RenderMapping(params = "action=registerUser")
	public String registerUser() {
		logger.info("Showing register User Form");

		return "register";
	}
	
    
    @ActionMapping("doRegisterUser")
    public void doRegisterUser(Model model,
	    @Valid @ModelAttribute(REGISTER_USER_MODEL) RegisterUserRequest registerUserRequest,
	    BindingResult bindingResult, ActionRequest request, ActionResponse response) {
    	
    	logger.info("register user.");
    	
    	registerUserRequestValidator.validate(registerUserRequest, bindingResult);
    	
		if (bindingResult.hasErrors()) {
			logger.info("Errors detected on input form, retry register user.");
	    	
        	model.addAttribute("status", "WRONG_DATA");
        	
        	response.setRenderParameter("state", "change-password");
		}

		else {
			logger.debug("No formal errors found on form. Proceeding with register user.");
			
			registerUserRequest.setFirstName(registerUserRequest.getFirstName().replaceAll("\\s+",".").toLowerCase());
			registerUserRequest.setLastName(registerUserRequest.getLastName().replaceAll("\\s+",".").toLowerCase());
	    
	    RegisterUserResponse registerUserResponse = restTemplate.postForObject(
			    registerUserURL, registerUserRequest, RegisterUserResponse.class);

	    RegisterUserStatusType status = registerUserResponse.getStatus();
	    
	    switch (status) {
	    case SUCCESS:
	    	logger.info("-SUCCESS");
	    	
        	model.addAttribute("status", "SUCCESS");
        	model.addAttribute("registerUserRequest", registerUserRequest);
		break;
	    case WRONG_DATA:
	    	logger.info("-WRONG_DATA");
	    	
        	model.addAttribute("status", "WRONG_DATA");
		break;
	    case USER_EXIST:
	    	logger.info("-USER_EXIST");
	    	
	    	model.addAttribute("status", "USER_EXIST");
		break;
	   
	    default:
		logger.warn("Default behavior detected. Unsupported ChangePasswordStatus intercepted: {}.", status);

		bindingResult.reject("authentication-failure");
	    }
	}

	response.setRenderParameter("action", "registerUser");
    }

	@RenderMapping(params = "display=success")
    public String showSuccess(Model model) {
		model.addAttribute("status", "FIRST");
    	return "view";
    }
	
	
	
	@ActionMapping(value = "userChangeStatus")
	public void userChangeStatus(@RequestParam(value = "userId", required = false) String userId,
			  ActionResponse response){
		
		UserIdentificationType userIdentificationType = new UserIdentificationType();
		ChangeStatusRequest changeStatusRequest = new ChangeStatusRequest ();
		
		userIdentificationType.setUserId(Long.valueOf(userId));
		changeStatusRequest.setUserId(userIdentificationType);
		
		restTemplate.postForObject(userChangeStatusURL, changeStatusRequest, ChangeStatusResponse.class);

		}
    
}




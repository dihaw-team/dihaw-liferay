package com.dihaw.portal.liferay.register;

import com.dihaw.portal.liferay.validators.RegisterUserRequestValidator;
import com.dihaw.web.schemas.profile.RegisterUserRequest;
import com.dihaw.web.schemas.profile.RegisterUserResponse;
import com.dihaw.web.schemas.profile.RegisterUserStatusType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping({"VIEW"})
public class RegisterUserController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	  public static final String REGISTER_USER_MODEL = "registerUser";
	  private String registerUserURL = "http://localhost:8088/dihaw-profile-services-sd/rest/register/registerUser";

	  @Autowired
	  private RestTemplate restTemplate;

	  @Autowired
	  RegisterUserRequestValidator registerUserRequestValidator;

	  @ModelAttribute("registerUser")
	  public RegisterUserRequest getChangePassword(PortletSession session) { 
		  RegisterUserRequest registerUserRequest = new RegisterUserRequest();
	    return registerUserRequest; }

	  @RenderMapping
	  public String showView(Model model, RenderRequest request) throws SystemException, PortalException
	  {
	    System.out.println("---show Register User View");
	    this.logger.info("Showing Register view");

	    return "view";
	  }

	  @RenderMapping(params={"action=registerUser"})
	  public String registerUser() {
	    System.out.println("");
	    this.logger.info("Showing register User Form");

	    return "register";
	  }

	  @ActionMapping("doRegisterUser")
	  public void doRegisterUser(Model model, @Valid @ModelAttribute("registerUser") RegisterUserRequest registerUserRequest, BindingResult bindingResult, ActionRequest request, ActionResponse response)
	  {
	    System.out.println("--register user.");
	    this.logger.info("register user.");

	    this.registerUserRequestValidator.validate(registerUserRequest, bindingResult);

	    if (bindingResult.hasErrors()) {
	      this.logger.info("Errors detected on input form, retry register user.");
	      System.out.println("--bindingResult.hasErrors()");

	      model.addAttribute("status", "WRONG_DATA");

	      response.setRenderParameter("state", "change-password");
	    }
	    else
	    {
	      this.logger.debug("No formal errors found on form. Proceeding with register user.");
	      System.out.println("-------1-----------RegisterUserResponse registerUserResponse");

	      RegisterUserResponse registerUserResponse = (RegisterUserResponse)this.restTemplate.postForObject(this.registerUserURL, registerUserRequest, RegisterUserResponse.class, new Object[0]);

	      System.out.println("-------2-----------RegisterUserResponse registerUserResponse");

	      RegisterUserStatusType status = registerUserResponse.getStatus();

	      switch (1) {
	      case 1:
	        System.out.println("--SUCCESS");
	        this.logger.info("-SUCCESS");

	        model.addAttribute("status", "SUCCESS");
	        model.addAttribute("registerUserRequest", registerUserRequest);
	        break;
	      case 2:
	        System.out.println("--WRONG_DATA");
	        this.logger.info("-WRONG_DATA");

	        model.addAttribute("status", "WRONG_DATA");
	        break;
	      case 3:
	        System.out.println("--USER_EXIST");
	        this.logger.info("-USER_EXIST");

	        model.addAttribute("status", "USER_EXIST");
	        break;
	      default:
	        this.logger.warn("Default behavior detected. Unsupported ChangePasswordStatus intercepted: {}.", status);

	        bindingResult.reject("authentication-failure");
	      }
	    }

	    response.setRenderParameter("action", "registerUser");
	  }

	  @RenderMapping(params={"display=success"})
	  public String showSuccess(Model model)
	  {
	    model.addAttribute("status", "FIRST");
	    return "view";
	  }

}

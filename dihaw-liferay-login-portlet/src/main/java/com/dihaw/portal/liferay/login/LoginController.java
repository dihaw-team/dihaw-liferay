package com.dihaw.portal.liferay.login;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

import com.dihaw.portal.liferay.LoginUtils;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;
import com.dihaw.web.schemas.profile.LoginStatusType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

@Controller
@SessionAttributes(LoginController.LOGIN_RESULT)
@RequestMapping(value = "VIEW", params = "!state")
public class LoginController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String LOGIN_MODEL = "login";
    public static final String LOGIN_RESULT = "loginResponse";
    public static final String EXPIRING_DAYS = "expiringDays";
    public static final String ALLOWED_METHODS_SEPARATOR = ",";
    public static final String ACCESS_TYPE = "accessType";
    public static final String ACCESS_TYPE_2_PARAMS = "2-params";
    public static final String ACCESS_TYPE_DENY = "deny";
    public static final String ACCESS_TYPE_3_PARAMS = "3-params";
    public static final String WRONG_PASSWORD_STATUS_MODEL = "wrongPasswordStatus";
    public static final String WRONG_PASSWORD_STATUS_VALUE_MODEL = "errorPassword";
    public static final String DEVICE_PRINT_NAME = "device-print-name";
    
	private String performLoginURI = "http://localhost:8088/dihaw-profile-services-sd/rest/login/performLogin";

    @Autowired
    @Qualifier("loginRequestValidator")
    private Validator validator;
    
    @Autowired
    private RestTemplate restTemplate;
    

    @InitBinder("loginRequestValidator")
    public void initBinder(PortletRequestDataBinder binder) {
	binder.setValidator(validator);
    }

    /**
     * Create the login DTO model.
     * 
     * @return the {@link LoginDTO}
     */
    @ModelAttribute(LOGIN_MODEL)
    public LoginRequest getLoginForm() {
	LoginRequest loginRequest = new LoginRequest();
	
	return loginRequest;
    }
    


    /**
     * Default render mapping.
     * <p>
     * Invoked when no more specific render is provided. Provides two different output, depending if the user is
     * currently logged in or not.<br>
     * If the user is currently logged in the "logged-in" view is show, with some user data in; if the user is not
     * logged in the "login" view is shown.
     * 
     * @return the name of the view to render
     * @throws SystemException if an Liferay system error occurs
     * @throws PortalException if a portal error occurs
     */
    @RenderMapping(params = "!display")
    public String showLoginForm(Model model, RenderRequest request) throws SystemException, PortalException {

    ThemeDisplay td = LoginUtils.getThemeDisplay();
	model.addAttribute("loginURL", LoginUtils.getPrivateHomepageURL(td.getUser()));

	HttpServletRequest httpRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
	String method = httpRequest.getMethod();
	String allowedAccessParameterName = Preferences.getAllowedAccessParameterName();
	String paramAccessApplication = httpRequest.getParameter(allowedAccessParameterName);
	
	boolean checkAccessStatus = checkParamFromApplication(paramAccessApplication, method, model);
	
	// if access parameter is necessary and not supplied and user haven't entered a wrong password, show an access deny
	if (StringUtils.isBlank(getWrongPasswordFromModel(model)) && !checkAccessStatus) {
		return "access-deny";
	}
		
	return "login";
    }
    
    /**
     * Read wrong status password from model
     * @param model
     * @return
     */
    private String getWrongPasswordFromModel(Model model) {
		return (String) model.asMap().get(WRONG_PASSWORD_STATUS_MODEL);
	}
    
    /**
     * Write wrong status password from model
     * @param model
     */
    private void setWrongPasswordFromModel(Model model) {
    	String statusPassword = (String) model.asMap().get(WRONG_PASSWORD_STATUS_MODEL);
		if (StringUtils.isNotBlank(statusPassword))
			model.asMap().put(WRONG_PASSWORD_STATUS_MODEL, WRONG_PASSWORD_STATUS_VALUE_MODEL);
		else
			model.addAttribute(WRONG_PASSWORD_STATUS_MODEL, WRONG_PASSWORD_STATUS_VALUE_MODEL);
	}
    
    /**
     * Clear wrong status password from model
     * @param model
     */
    private void clearWrongPasswordFromModel(Model model) {
    	model.asMap().remove(WRONG_PASSWORD_STATUS_MODEL);
	}
    
    

    /**
     * Function check if the Login Page (or Single Sign On entrance) is reachable directly from the web or only by a specific application.
     * The application would integrate the portal MUST pass an Authorization Token in the specified method (i.e. GET,POST, etc.).
     *   
     * @param fromAddress
     * @param method
     * @param model 
     * @return true - Login Page visible / false - Access Deny
     */
    private boolean checkParamFromApplication(String paramAccessApplication, String method, Model model) {
    	method = StringUtils.upperCase(method);
    	String authorizationToken = Preferences.getAllowedAccessParameter();
    	String allowedMethod = Preferences.getAllowedAccessMethods();
    	String[] arrayMethods = getHttpMethodsArray(allowedMethod);
    	if (StringUtils.isNotBlank(authorizationToken)) {
    		if (authorizationToken.equalsIgnoreCase(paramAccessApplication)) {
    			if (ArrayUtils.contains(arrayMethods, method)) {
    				model.asMap().put(ACCESS_TYPE, ACCESS_TYPE_2_PARAMS);
    				return true;
    			}
    		}
    		model.asMap().put(ACCESS_TYPE, ACCESS_TYPE_DENY);
    		return false;
    	}
    	model.asMap().put(ACCESS_TYPE, ACCESS_TYPE_3_PARAMS);
		return true;
	}

	private String[] getHttpMethodsArray(String allowedMethod) {
		allowedMethod = StringUtils.upperCase(allowedMethod);
    	allowedMethod = StringUtils.trimToEmpty(allowedMethod);
    	allowedMethod = StringUtils.replaceChars(allowedMethod, " ", "");
		return StringUtils.split(allowedMethod, ALLOWED_METHODS_SEPARATOR);
	}

	/**
     * Perform the login and redirect the user to one of the render view depending on the login operation result.
     * 
     * @param response the <tt>ActionResponse</tt> to display the view to
     * @throws PortalException if a portal business error occurs
     * @throws SystemException if a portal system error occurs
     * @throws IOException if an error occurs during client redirect
     */
	
    @ActionMapping("performLogin")
    public void performUserLogin(Model model, @Valid @ModelAttribute(LOGIN_MODEL) LoginRequest loginRequest,
	    BindingResult bindingResult, ActionRequest request, ActionResponse response, PortletSession session)
	    throws PortalException, SystemException, IOException {
    	
	if (!bindingResult.hasErrors()) {
		
	    logger.debug("Trying to perform remote login operation.");
	    
	    LoginResponse loginResponse = restTemplate.postForObject(performLoginURI, loginRequest, LoginResponse.class);
	    
	    System.out.println("--LoginController------------loginResponse.userStatus: "+loginResponse.getUserStatus());
	    System.out.println("--LoginController------------loginResponse.loginStatus: "+loginResponse.getLoginStatus());
	    
//	    LoginStatusType status = loginResponse.getLoginStatus();
	    
//	    String userStatus = loginResponse.getUserStatus();
    	
//	    logger.debug("Remote service login result in a '{}' enable.", userStatus);
	    
	    switch (loginResponse.getLoginStatus()) {
	    case SUCCESS:
	    	
	    	System.out.println("--LoginController------------SUCCESS");
	    	
		    logger.debug("Creating or updating local user with data from object {}.", loginResponse);
		    try{
		    	User user = LoginUtils.createOrUpdateLocalUser(loginRequest, loginResponse);
		    	LoginUtils.performLogin(user, request, response);
		    }
		    catch(PortalException e){
		    }
	    	
	    break;
	    case FIRST_LOGIN:
	    	
	    	System.out.println("--LoginController------------FIRST_LOGIN");
	    		    	
	    	logger.debug("First login detected. Force password change.");

			session.setAttribute(LOGIN_MODEL, loginRequest);
			session.setAttribute(LOGIN_RESULT, loginResponse);
			
			response.setRenderParameter("state", "password-expired");
			response.setRenderParameter("display", "first-login");
			model.addAttribute("status", "FIRST_LOGIN");
			clearWrongPasswordFromModel(model);
	    	
		break;
	    case NOT_FOUND:
	    	
	    	System.out.println("--LoginController------------NOT_FOUND");
	    	
		    bindingResult.rejectValue("emailAddress", "wrong-credentials");
		    bindingResult.rejectValue("password", "wrong-credentials");
		    model.addAttribute("status", "NOT_FOUND");
		    setWrongPasswordFromModel(model);
	    	
		break;
	    case ACCESS_DENIED:
	    	
	    	System.out.println("--LoginController------------ACCESS_DENIED");
	    	
		    bindingResult.rejectValue("emailAddress", "wrong-credentials");
		    bindingResult.rejectValue("password", "wrong-credentials");
		    model.addAttribute("status", "ACCESS_DENIED");
		    setWrongPasswordFromModel(model);
	    	
		break;
	    case WRONG_PASSWORD:
	    	
	    	System.out.println("--LoginController------------WRONG_PASSWORD");
	    	
		    bindingResult.rejectValue("emailAddress", "wrong-credentials");
			bindingResult.rejectValue("password", "wrong-credentials");
			model.addAttribute("status", "WRONG_PASSWORD");
			setWrongPasswordFromModel(model);
		default:
			break;
	    
	    }


	}
    	
    }

    /**
     * Check function to validate the range of the contract code number
     * 
     * @param contractCode
     * @return false if the contract code in between the minimum and the maximum value set, true otherwise.
     */
    private boolean checkWrongRangeContractCode(String contractCode) {
	
	int minContractCode = 0;
	int maxContractCode = 0;
	try {
		minContractCode = NumberUtils.toInt("000000");
        maxContractCode = NumberUtils.toInt("999999");
            
	} catch (NumberFormatException nfe) {
	    logger.error("NumberFormatException in configuration of properties MinimumContractCodeValue or MaximumContractCodeValue", nfe);
	    return true;
	}
	
	try {
	    String contractCodeValueString = StringUtils.right(contractCode, 6);
//	    String contractCodeValueString = StringUtils.right(contractCode, Preferences.getContractCodeNumberLength());

	    int contractCodeValue = NumberUtils.toInt(contractCodeValueString);
	    
	    if ((contractCodeValue >= minContractCode) && (contractCodeValue <= maxContractCode))
		return false;
	    else
		return true;
	    
	} catch (NumberFormatException nfe) {
	    return true;
	}
    }

    @ActionMapping("continue")
    public void doContinue(ActionRequest request, ActionResponse response, PortletSession session)
	    throws PortalException, SystemException, IOException {
    	
	LoginRequest loginRequest = (LoginRequest) session.getAttribute(LOGIN_MODEL);
	LoginResponse loginResponse = (LoginResponse) session.getAttribute(LOGIN_RESULT);
	
	session.removeAttribute(EXPIRING_DAYS);
	session.removeAttribute(LOGIN_MODEL);
	session.removeAttribute(LOGIN_RESULT);
	
	User user = LoginUtils.createOrUpdateLocalUser(loginRequest, loginResponse);
	LoginUtils.performLogin(user, request, response);
    }
    
    
}

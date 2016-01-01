package com.dihaw.portal.liferay;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;
import com.dihaw.web.schemas.profile.UserIdentificationType;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**		Static utility class for login methods.		*/
public abstract class LoginUtils {
	
	
    /** The logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUtils.class);

    /**
     * Create a new local user starting from the data held in the provided login response data.
     * 
     * @param loginRequest the {@link LoginRequest} object
     * @param response the {@link LoginResponse} object
     * @return the newly created user
     * @throws PortalException if a portal error occurs during user creation
     * @throws SystemException if a system error occurs during user creation
     */
    public static final User createOrUpdateLocalUser(LoginRequest request, LoginResponse response)
	    throws SystemException, PortalException {
    	    	
	ThemeDisplay td = getThemeDisplay();
	long companyId = td.getCompanyId();
	String firstName = response.getFirstName();
	String lastName = response.getLastName();
	
	String name = response.getFirstName()+"."+response.getLastName();
	name = name.replaceAll("\\s+",".").toLowerCase();
	String terminalCode = "dihaw";
	
	String emailAddress = String.format("%s@%s.com", name, terminalCode);
	
	String middleName = null;

	Company company = CompanyLocalServiceUtil.getCompanyByWebId("liferay.com");
    	
	User user = null;
	try {
		
		System.out.println("------------companyId: "+companyId);
		System.out.println("------------emailAddress: "+emailAddress);
		
		user = UserLocalServiceUtil.getUserByEmailAddress(companyId, emailAddress);
	
	} catch (NoSuchUserException nsue) {
		LOGGER.debug("No user found, creating it !");
		              
              final Calendar birthDate = Calendar.getInstance();
              Calendar birthday = Calendar.getInstance();
              birthday.set(Calendar.DAY_OF_MONTH, Integer.parseInt("1"));
              birthday.set(Calendar.MONTH, Integer.parseInt("2") - 1);
              birthday.set(Calendar.YEAR, Integer.parseInt("1960"));
              birthDate.setTime(birthday.getTime());
              
          user = UserLocalServiceUtil.addUser(
                  company.getDefaultUser().getUserId(),
                  company.getCompanyId(),
                  /* autoPassword */ true, /* password1 */ null, /* password2 */ null,
                  /* autoScreenName */ true, /* screenName */ null,
                  /* emailAddress */ emailAddress, 
                  /* facebookId */ 0, /* openId */ "",
                  /* locale */ Locale.FRANCE,
                  /* firstName */ firstName,
                  /* middleName */ middleName,
                  /* lastName */ lastName,
                  /* prefixId */ 0, /* suffixId */ 0,
                  /* male */ true,
                  /* birthdayMonth */ birthDate.get(Calendar.MONTH),
                  /* birthdayDay */ birthDate.get(Calendar.DAY_OF_MONTH),
                  /* birthdayYear */ birthDate.get(Calendar.YEAR),
                  /* jobTitle */ "",
                  /* groupIds */ new long[] { 10180 },
                  /* organizationIds */ new long[0],
                  /* roleIds */ new long[0],
                  /* userGroupIds */ new long[0],
                  false,
                  null);   
                   
          		user.setPasswordReset(false);
          		user.setReminderQueryQuestion("Question");
          		user.setReminderQueryAnswer("Answer");
          		UserLocalServiceUtil.updateUser(user);
          		
              return user;
	}
	
	 return user;
	
    }


    /**
     * Retrieve the current {@link ThemeDisplay} object from the current request.
     * 
     * @return the {@link ThemeDisplay} object or <code>null</code>
     */
    public static final ThemeDisplay getThemeDisplay() {
	return (ThemeDisplay) RequestContextHolder.currentRequestAttributes().getAttribute(WebKeys.THEME_DISPLAY,
		RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Retrieve the URL of the private page for the user currently logged in.
     * @param user 
     * 
     * @return the URL of the first private page
     * @throws SystemException if an Liferay system error occurs
     * @throws PortalException if a portal errorccurs
     */
    public static String getPrivateHomepageURL(User user) throws SystemException, PortalException {
	ThemeDisplay td = getThemeDisplay();
	String privateHomepageURL = null;

	List<Layout> privateLayouts = LayoutLocalServiceUtil.getLayouts(td.getScopeGroup().getGroupId(), true);
	if (!privateLayouts.isEmpty()) {
	    LOGGER.debug("Private layouts found: retrieving URL for the first page with correct permission.");
	    Layout privateHome = privateLayouts.get(0);
	    privateHome = getFirstLayoutWithPermission(user, privateLayouts, privateHome);
	    privateHomepageURL = PortalUtil.getLayoutFriendlyURL(privateHome, td);
	} else {
	    privateHomepageURL = getPublicHomepageURL();
	}

	return privateHomepageURL;
    }

    /**
     * Retrive the private landing page URL. This page is the first page in the menu's tree where the user have the view permission.
     * 
     * @param td
     * @param privateLayouts
     * @param privateHome
     * @return
     */
	private static Layout getFirstLayoutWithPermission(User user,
			List<Layout> privateLayouts, Layout privateHome) {
		
		try {
			for (Layout privateLayout : privateLayouts) {
		    	boolean havePermission = LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(user, false), privateLayout, ActionKeys.VIEW);
		    	if (havePermission) {
		    		privateHome = privateLayout;
		    		break;
		    	}
			}
		} catch (Exception e) {
			LOGGER.error("LoginUtils.getFirstLayoutWithPermission: errore sul controllo dei permessi per l'accesso alla pagina", e);
		}
		return privateHome;
	}

    /**
     * Retrieve the URL of the public page for the user currently logged in.
     * 
     * @return the URL of the first private page
     * @throws SystemException if an Liferay system error occurs
     * @throws PortalException if a portal error occurs
     */
    public static String getPublicHomepageURL() throws SystemException, PortalException {
	ThemeDisplay td = getThemeDisplay();
	String publicHomepageURL = null;

	List<Layout> publicLayouts = LayoutLocalServiceUtil.getLayouts(td.getScopeGroup().getGroupId(), false);
	if (!publicLayouts.isEmpty()) {
	    LOGGER.debug("Public layouts found: retrieving URL for the first page.");

	    Layout publicHome = publicLayouts.get(0);
	    publicHomepageURL = PortalUtil.getLayoutFriendlyURL(publicHome, td);
	    
	    System.out.println("--getPublicHomepageURL--------publicHomepageURL: "+publicHomepageURL);
	}

	return publicHomepageURL;
    }

    /**
     * Retrieve the sign out URL for the user retrieved from the provided {@code ThemeDisplay} object.
     * 
     * @return the sign out URL, never <code>null</code>
     * @throws SystemException if an Liferay system error occurs
     * @throws PortalException if a portal errorccurs
     */
    public static String getSignOutURL() throws SystemException, PortalException {
	ThemeDisplay td = getThemeDisplay();
	StringBuilder signOutURL = new StringBuilder(td.getURLSignOut());

	String publicHomepageURL = getPublicHomepageURL();
	if (publicHomepageURL != null) {
	    signOutURL.append("?referer=").append(publicHomepageURL);
	}

	return signOutURL.toString();
    }

    /**
     * Perform the login for the data stored in the provided request/response pair.
     * 
     * @param user the user id for which perform the login operation
     * @param request the action request
     * @param response the action response
     * @throws IOException if the redirect operation fails
     */
    public static void performLogin(User user, ActionRequest request, ActionResponse response) throws IOException {
	try {
		
	    LOGGER.info("Login successiful, redirecting to private homepage url.");

	    HttpServletRequest servletRequest = PortalUtil.getHttpServletRequest(request);
	    UserLocalServiceUtil.updateLastLogin(user.getUserId(), servletRequest.getRemoteAddr());

	    HttpSession session = servletRequest.getSession(true);
	    session.setAttribute(WebKeys.USER_ID, user.getUserId());
	    
	    response.sendRedirect("/web/dihaw/home");
	    
	    
	} catch (PortalException e) {
	    LOGGER.error("PortalException detected.", e);
	} catch (SystemException e) {
	    LOGGER.error("SystemException detected.", e);
	}
    }

    /**
     * Retrieve the current user id.
     * 
     * @return the current user id
     */
    public static UserIdentificationType getCurrentUserId() {
	long companyId = getThemeDisplay().getCompanyId();
	User user = getThemeDisplay().getUser();
	try {
	    UserIdentificationType userId = new UserIdentificationType();
	    userId.setUserId(ExpandoValueLocalServiceUtil.getData(companyId, User.class.getName(),
		    ExpandoTableConstants.DEFAULT_TABLE_NAME, "web-user-id", user.getUserId(), 0l));
	    userId.setTerminalId(ExpandoValueLocalServiceUtil.getData(companyId, User.class.getName(),
		    ExpandoTableConstants.DEFAULT_TABLE_NAME, "web-terminal-id", user.getUserId(), 0l));
	    
	    return userId;
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe).addContextValue("user", user);
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se).addContextValue("user", user);
	}
    }
}

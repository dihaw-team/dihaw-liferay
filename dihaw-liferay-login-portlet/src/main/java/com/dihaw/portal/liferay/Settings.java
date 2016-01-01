package com.dihaw.portal.liferay;

import static org.apache.commons.lang3.StringUtils.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.DefaultExceptionContext;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**		Class used to store general settings.	 */
public abstract class Settings {
    /**
     * The name of the portlet preference to read/write for get/set the ABI code.
     */
    private static final String ABI_CODE = "abi-code";
    
    /**
     * The name of the portlet preference to read/write for get/set the contract code minimum value
     */
    public static final String CONTRACT_CODE_MINIMUM_VALUE_COLUMN_NAME = "contract-code-minimum-value";
    
    /**
     * The name of the portlet preference to read/write for get/set the contract code maximum value
     */
    public static final String CONTRACT_CODE_MAXIMUM_VALUE_COLUMN_NAME = "contract-code-maximum-value";
    
    /**
     * The name of the portlet preference to read/write for get/set the remote login URL.
     */
    private static final String REMOTE_SERVICES_URL = "remote-services-url";
    
    /**
     * The name of the portlet preference to read/write for get/set the allowed external access parameter
     */
    private static final String ALLOWED_ACCESS_PARAMETER = "allowed-access-parameter";
    
    /**
     * The name of the portlet preference to read/write for get/set the name of the allowed external access parameter
     */
    private static final String ALLOWED_ACCESS_PARAMETER_NAME = "allowed-access-parameter-name";
    
    /**
     * The name of the portlet preference to read/write for get/set the allowed http methods used for exchange parameter
     */
    private static final String ALLOWED_ACCESS_METHODS = "allowed-access-methods";
    
    /**
     * The name of the portlet preference to read/write for get/set the terminal code parameter
     */
    private static final String TERMINAL_CODE_PARAMETER_NAME = "terminal-code-parameter-name";
    
    /**
     * The class name of the organization.
     */
    private static final String ORGANIZATION_CLASS_NAME = Organization.class.getName();
    /**
     * Name of the expando table to use.
     */
    private static final String EXPANDO_TABLE_NAME = ExpandoTableConstants.DEFAULT_TABLE_NAME;

    /**
     * Retrieve the remote login URI
     * 
     * @return the URI
     */
    public static final URI getRemoteLoginURL() {
	String value = getRemoteServicesBaseURL() + "/profile-services/rest/login/performLogin";
	try {
	    return new URI(value);
	} catch (URISyntaxException e) {
	    throw new ContextedRuntimeException("Invalid URL provided for remote login.", e,
		    new DefaultExceptionContext().addContextValue("url", value));
	}
    }

    /**
     * Retrieve the remote change password URI.
     * 
     * @return the URI
     */
    public static final URI getRemoteChangePasswordURL() {
	String value = getRemoteServicesBaseURL() + "/profile-services/rest/login/changePassword";
	try {
	    return new URI(value);
	} catch (URISyntaxException e) {
	    throw new ContextedRuntimeException("Invalid URL provided for remote login.", e,
		    new DefaultExceptionContext().addContextValue("url", value));
	}
    }

    /**
     * Retrieve the remote role list URI.
     * 
     * @return the URI
     */
    public static final URI getRemoteRoleListURL() {
	String value = getRemoteServicesBaseURL() + "/profile-services/rest/sync/roles";
	try {
	    return new URI(value);
	} catch (URISyntaxException e) {
	    throw new ContextedRuntimeException("Invalid URL provided for remote login.", e,
		    new DefaultExceptionContext().addContextValue("url", value));
	}
    }

    /**
     * Retrieve the remote change password and login URI.
     * 
     * @return the URI
     */
    public static final URI getRemoteChangePasswordAndLoginURL() {
	String value = getRemoteServicesBaseURL() + "/profile-services/rest/login/changePasswordAndLogin";
	try {
	    return new URI(value);
	} catch (URISyntaxException e) {
	    throw new ContextedRuntimeException("Invalid URL provided for remote login.", e,
		    new DefaultExceptionContext().addContextValue("url", value));
	}
    }

    /**
     * Retrieve the ABI code for the login.
     * 
     * @return the ABI code
     */
    public static final String getAbiCode() {
	try {
	    String abiCode = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
		abiCode = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, ABI_CODE, group.getClassPK(), EMPTY);
	    }
	    return abiCode;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    
    /**
     * Retrieve the Minimum Contract Code value for the login.
     * 
     * @return the Minimum Contract Code value
     */
    public static final String getMinimumContractCodeValue() {
	try {
	    String minimumContractCodeValue = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
		minimumContractCodeValue = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, CONTRACT_CODE_MINIMUM_VALUE_COLUMN_NAME, group.getClassPK(), EMPTY);
	    }
	    return minimumContractCodeValue;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    
    /**
     * Retrieve the Maximum Contract Code value for the login.
     * 
     * @return the Maximum Contract Code value
     */
    public static final String getMaximumContractCodeValue() {
	try {
	    String maximumContractCodeValue = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
		maximumContractCodeValue = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, CONTRACT_CODE_MAXIMUM_VALUE_COLUMN_NAME, group.getClassPK(), EMPTY);
	    }
	    return maximumContractCodeValue;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    
    /**
     * Retrieve the allowed external access parameter
     * 
     * @return the Maximum Contract Code value
     */
    public static final String getAllowedAccessParameter() {
	try {
	    String allowedAccessParameter = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
	    	allowedAccessParameter = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, ALLOWED_ACCESS_PARAMETER, group.getClassPK(), EMPTY);
	    }
	    return allowedAccessParameter;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }

    /**
     * Retrieve the allowed external access parameter name
     * 
     * @return the Maximum Contract Code value
     */
    public static final String getAllowedAccessParameterName() {
	try {
	    String allowedAccessParameterName = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
	    	allowedAccessParameterName = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, ALLOWED_ACCESS_PARAMETER_NAME, group.getClassPK(), EMPTY);
	    }
	    return allowedAccessParameterName;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    
    /**
     * Retrieve the allowed http methods used to exchange the external access parameter
     * 
     * @return the Maximum Contract Code value
     */
    public static final String getAllowedAccessMethods() {
	try {
	    String allowedAccessMethods = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
	    	allowedAccessMethods = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, ALLOWED_ACCESS_METHODS, group.getClassPK(), EMPTY);
	    }
	    return allowedAccessMethods;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    
    /**
     * Retrieve the allowed http methods used to exchange the terminal code parameter
     * 
     * @return the Maximum Contract Code value
     */
    
    /*
    public static final String getTerminalCodeParameterName() {
	try {
	    String allowedAccessMethods = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
	    	allowedAccessMethods = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME,
			EXPANDO_TABLE_NAME, TERMINAL_CODE_PARAMETER_NAME, group.getClassPK(), EMPTY);
	    }
	    return allowedAccessMethods;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
    */
    private static final String getRemoteServicesBaseURL() {
	try {
	    String remoteServicesURL = null;
	    ThemeDisplay themeDisplay = LoginUtils.getThemeDisplay();
	    Group group = themeDisplay.getLayout().getGroup();
	    if (group.isOrganization()) {
		remoteServicesURL = ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(),
			ORGANIZATION_CLASS_NAME, EXPANDO_TABLE_NAME, REMOTE_SERVICES_URL, group.getClassPK(), EMPTY);
	    } else {
		group = GroupLocalServiceUtil.getGroup(themeDisplay.getDoAsGroupId());
		if (group.isOrganization()) {
		    remoteServicesURL = ExpandoValueLocalServiceUtil
			    .getData(themeDisplay.getCompanyId(), ORGANIZATION_CLASS_NAME, EXPANDO_TABLE_NAME,
				    REMOTE_SERVICES_URL, group.getClassPK(), EMPTY);
		}
	    }
	    return remoteServicesURL;
	} catch (SystemException se) {
	    throw new ContextedRuntimeException(se);
	} catch (PortalException pe) {
	    throw new ContextedRuntimeException(pe);
	}
    }
}
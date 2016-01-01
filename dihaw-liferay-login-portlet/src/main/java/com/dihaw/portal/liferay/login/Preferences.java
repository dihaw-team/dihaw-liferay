package com.dihaw.portal.liferay.login;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.dihaw.portal.liferay.Settings;

/**
 * Class used to access portlet preferences.
 * 
 */
abstract class Preferences extends Settings {
    /** The logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Preferences.class);

    /**
     * The name of the portlet preference to read/write to get/set the changing of username on first login.
     */
    public static final String CHANGE_USERNAME_ON_FIRST_LOGIN = "changeUsernameOnFirstLogin";
    /**
     * The name of the portlet preference to read/write for get/set the number of expiring days.
     */
    public static final String WARNING_DAYS = "warningDays"; 
    /**
     * The name of the portlet preference to read/write for get/set the number of contract code number length
     */
    public static final String CONTRACT_CODE_NUMBER_LENGTH = "contractCodeNumberLength";

    /**
     * Retrieve the flag for changing the username on first login.
     * 
     * @return <code>true</code> if on first login the user can change the username and <code>false</code> otherwise
     */
    public static final boolean isChangeUsernameOnFirstLogin() {
	String value = getPortletPreferences().getValue(CHANGE_USERNAME_ON_FIRST_LOGIN, Boolean.FALSE.toString());

	return Boolean.parseBoolean(value);
    }

    /**
     * Set the flag for allowing the username change on first login.
     * 
     * @param value <code>true</code> if on first login the user can change the username and <code>false</code>
     *            otherwise
     */
    public static final void setChangeUsernameOnFirstLogin(boolean value) {
	try {
	    getPortletPreferences().setValue(CHANGE_USERNAME_ON_FIRST_LOGIN, Boolean.toString(value));
	} catch (ReadOnlyException roe) {
	    LOGGER.error("Unable to set the '" + CHANGE_USERNAME_ON_FIRST_LOGIN + "' portlet preference.", roe);
	}
    }

    /**
     * Retrieve the number of days for displaying the password expiration warning.
     * 
     * @return the number of days
     */
    public static final int getWarningDays() {
	String value = getPortletPreferences().getValue(WARNING_DAYS, "10");
	return Integer.parseInt(value, 10);
    }

    /**
     * Set the number of days for displaying the password expiration warning.
     * 
     * @param expiringDays the number of days to set
     */
    public static final void setWarningDays(int expiringDays) {
	Validate.isTrue(expiringDays >= 0, "Number of expiring days must be greater or equals than %d.", 0);

	try {
	    getPortletPreferences().setValue(WARNING_DAYS, Integer.toString(expiringDays, 10));
	} catch (ReadOnlyException roe) {
	    LOGGER.error("Unable to set the '" + WARNING_DAYS + "' portlet preference.", roe);
	}
    }

    /**
     * Retrieve the portlet preferences from the current request using the {@link RequestContextHolder} class.
     * 
     * @return the {@link PortletPreferences} or <code>null</code>
     */
    private static final PortletPreferences getPortletPreferences() {
	PortletRequest request = (PortletRequest) RequestContextHolder.currentRequestAttributes().resolveReference(
		RequestAttributes.REFERENCE_REQUEST);
	return request.getPreferences();
    }
    
    /**
     * Retrieve the number of days for displaying the password expiration warning.
     * 
     * @return the number of days
     */
    public static final int getContractCodeNumberLength() {
	String value = getPortletPreferences().getValue(CONTRACT_CODE_NUMBER_LENGTH, "6");
	return Integer.parseInt(value, 10);
    }
}

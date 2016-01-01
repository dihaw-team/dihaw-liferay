package com.dihaw.portal.liferay.validators;

import java.util.regex.Pattern;

public class PasswordValidationUtils {
	
	private static final Pattern LOWER_CASE = Pattern.compile("\\p{Lu}");
    private static final Pattern UPPER_CASE = Pattern.compile("\\p{Ll}");
    private static final Pattern DECIMAL_DIGIT = Pattern.compile("\\p{Nd}");
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * Determine if a password is valid.
     * 
     * <p>
     * A password is considered valid if it contains:
     * <ul>
     * <li>At least 1 letter (lower-case or upper-case)</li>
     * <li>At least 1 digit</li>
     * <li>Min 8 chars</li>
     * <li>Max 20 chars</li>
     * </p>
     * 
     * @param password
     *            password to validate
     * @return True if the password is considered valid, otherwise false
     */
    public static boolean isValidPassword(String password) {
        return containsDigit(password) && containsLetter(password) && passwordLength(password);
    }

    private static boolean containsDigit(String str) {
        return DECIMAL_DIGIT.matcher(str).find();
    }
    
    private static boolean containsUpperCase(String str) {
    	return UPPER_CASE.matcher(str).find();
    }

    private static boolean containsLowerCase(String str) {
        return LOWER_CASE.matcher(str).find();
    }
    
    private static boolean containsLetter(String str) {
    	return containsUpperCase(str) || containsLowerCase(str);
    }
    
    private static boolean passwordLength(String str) {
    	return str.length() >= PASSWORD_MIN_LENGTH && str.length() <= PASSWORD_MAX_LENGTH;
    }
}

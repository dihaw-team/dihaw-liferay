package com.dihaw.web.services.profile.exception;

public abstract class UserException extends Exception {
	private static final long serialVersionUID = -1829110481264867195L;
	
    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.dihaw.web.services.profile.entity;

public enum UserStatus {

	ENABLED,
	DISABLED,
	BLOKED,
	WAITING_FIRST_LOGIN;
	
    public String value() {
        return name();
    }

    public static UserStatus fromValue(String v) {
        return valueOf(v);
    }

}

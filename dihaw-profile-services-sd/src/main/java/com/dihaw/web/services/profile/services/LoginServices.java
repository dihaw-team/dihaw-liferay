package com.dihaw.web.services.profile.services;

import com.dihaw.web.schemas.profile.ChangePasswordAndLoginRequest;
import com.dihaw.web.schemas.profile.ChangePasswordAndLoginResponse;
import com.dihaw.web.schemas.profile.ChangePasswordRequest;
import com.dihaw.web.schemas.profile.ChangePasswordResponse;
import com.dihaw.web.schemas.profile.LoginRequest;
import com.dihaw.web.schemas.profile.LoginResponse;

public interface LoginServices {
	
	
	LoginResponse login(LoginRequest request);

	ChangePasswordResponse changePassword(ChangePasswordRequest request);

	ChangePasswordAndLoginResponse changePasswordAndLogin(ChangePasswordAndLoginRequest request);

	LoginResponse getFirst();

}

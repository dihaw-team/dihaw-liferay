package com.dihaw.web.services.profile.services;

import java.util.List;

import com.dihaw.web.services.profile.dto.ProfileDTO;
import com.dihaw.web.services.profile.entity.Profile;

public interface ProfileServices {
	
	List<Profile> findAll();

	ProfileDTO profileByUserId(long userId);


}

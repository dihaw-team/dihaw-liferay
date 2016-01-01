package com.dihaw.web.services.profile.services;

import com.dihaw.web.services.profile.dto.ListAccountDTO;
import com.dihaw.web.services.profile.entity.Account;

public interface AccountServices {

	ListAccountDTO findAll();

	Account accountByUserId(long userId);

}

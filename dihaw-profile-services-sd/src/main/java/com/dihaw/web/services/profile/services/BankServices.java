package com.dihaw.web.services.profile.services;

import com.dihaw.web.services.profile.dto.ListBankDTO;
import com.dihaw.web.services.profile.entity.Bank;

public interface BankServices {

	ListBankDTO findAll();
	
	Bank bankByUserId(long userId);

}

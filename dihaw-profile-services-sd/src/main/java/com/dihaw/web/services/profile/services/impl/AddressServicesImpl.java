package com.dihaw.web.services.profile.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dihaw.web.services.profile.dto.ListAddressDTO;
import com.dihaw.web.services.profile.entity.Address;
import com.dihaw.web.services.profile.repository.AddressRepository;
import com.dihaw.web.services.profile.services.AddressServices;

@Service
@Transactional(propagation = Propagation.SUPPORTS) 
public class AddressServicesImpl implements AddressServices{
	
	@Autowired
	AddressRepository addressRepository;

	@Transactional(propagation = Propagation.REQUIRED) 
	public ListAddressDTO findAll() {
		
		ListAddressDTO list = new ListAddressDTO();
		
		List<Address> addressList = addressRepository.findAll();
		
		list.setList(addressList);

		return list; 
	}
	


}

package com.dihaw.web.services.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}

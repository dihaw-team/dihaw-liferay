package com.dihaw.web.services.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("select a from Account a where a.user.id= :userId")
	List<Account> findByUserId(@Param("userId") long userId);
	

}

package com.dihaw.web.services.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

	@Query("SELECT DISTINCT b FROM Bank b INNER JOIN b.accounts a WHERE a.user.id= :userId")
	List<Bank> findDistinctBankByUserId(@Param("userId") long userId);
	
}

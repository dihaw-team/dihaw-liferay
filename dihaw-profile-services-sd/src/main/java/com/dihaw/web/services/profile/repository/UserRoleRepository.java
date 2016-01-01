package com.dihaw.web.services.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository< UserRole, Long>{

	
	@Query("SELECT DISTINCT ur.role FROM UserRole ur WHERE ur.user.id= :userId")
	List<String> findByUserId(@Param("userId") long userId);

}

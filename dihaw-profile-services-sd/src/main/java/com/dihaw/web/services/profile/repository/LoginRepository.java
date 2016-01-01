package com.dihaw.web.services.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer>  {
	
//	@Query("select u from User u where u.email= :email and u.password= :password")
//	UserResponse findByid(	@Param("email") int email,
//							@Param("password") int password);
//
//	@Query("select u from User u where u.email= :request.emailAddress and u.password= :request.password")
//	LoginResponse performLogin(@Param("request") LoginRequest request);
	
	@Query("select u from User u where u.email= :email and u.password= :password")
	User performLogin(@Param("email") String email, @Param("password") String password);
	
	@Query("select u from User u where u.id=1")
	User getFirst();
	

}

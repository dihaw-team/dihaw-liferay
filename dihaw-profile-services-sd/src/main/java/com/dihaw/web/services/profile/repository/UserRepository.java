package com.dihaw.web.services.profile.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.User;
import com.dihaw.web.services.profile.entity.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    @Modifying
    @Query("update User u set u.status= :status where u.id= :id")	
	void changeStatus(@Param("id") int id, @Param("status") UserStatus userStatus);
    
	@Query("select count(u) from User u where u.username = :username")
	int count(@Param("username") String username);

    @Modifying
    @Query("update User u set u.lastConnection= :lastConnection where u.username= :username or u.email = :username")	
	void updateLastConnection(@Param("username") String username, @Param("lastConnection") Date lastConnection);
    
    @Query("from User u where u.username = :username or u.email = :username and u.password= :password")
	User findByUsernameAndPAssword(@Param("username") String username, @Param("password") String password);
    
    @Modifying
    @Query("update User u set u.accountNonExpired= :accountNonExpired where u.id= :id")	
	void changeAccountExpired(@Param("id") int id, @Param("accountNonExpired") int accountNonExpired);

    @Modifying
    @Query("update User u set u.accountNonLocked= :accountNonLocked where u.id= :id")	
	void changeAccountLocked(@Param("id") int id, @Param("accountNonLocked") int accountNonLocked);

    @Modifying
    @Query("update User u set u.credentialsNonExpired= :credentialsNonExpired where u.id= :id")	
	void changeCredentialsExpired(@Param("id") int id, @Param("credentialsNonExpired") int credentialsNonExpired);
	
    @Query("from User u where u.username = :username or u.email = :username")
	User findByUsername(@Param("username") String username);

    @Modifying 
    @Query("update User u set u.firstName= :firstName, u.lastName= :lastName, " +
    		"u.username= :username, u.email= :email, u.password= :password where u.id= :id")	 
	void updateUser(@Param("id") 		int id, 
					@Param("firstName") String firstName, 
					@Param("lastName")  String lastName, 
					@Param("username") 	String username, 
					@Param("email") 	String email, 
					@Param("password") 	String password
				);

}

package com.dihaw.web.services.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dihaw.web.services.profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query("select p from Profile p where p.user.id= :userId")
	Profile findProfilesByUserId(@Param("userId") long userId);

}

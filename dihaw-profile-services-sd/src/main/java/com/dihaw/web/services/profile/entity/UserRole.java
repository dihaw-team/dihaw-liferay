package com.dihaw.web.services.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 8282488477051928587L;
	
	@OneToOne
	@JsonManagedReference
	@JoinColumn(name="USER_ID")
	private User user;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
	private Role role;
	
	/**
     * constructor for ORM.
     */
	public UserRole(){
	}
	
	public UserRole(User user, Role role){
		this.user=user;
		this.role=role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	

}

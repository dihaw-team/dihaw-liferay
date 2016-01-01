package com.dihaw.web.services.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "PROFILE") 
public class Profile extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -4802047952967485435L;
	
	@OneToOne
	@JsonManagedReference
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name = "NAME")
	@NotBlank
	@Size(min = 3, max = 50)
	private String name;
	
	@Column(name = "DESCRIPTION")
	@NotBlank
	@Size(min = 3, max = 50)
	private String description;

	/**
     * Constructor for ORM.
     */
	public Profile(){
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}

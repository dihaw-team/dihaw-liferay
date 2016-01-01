package com.dihaw.web.services.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "ADDRESS") 
public class Address extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -5790935377175996319L;

	@Column(name = "STREET")
	@Size(min = 3, max = 50)
	private String street;
	
	@Column(name = "CITY")
	@Size(min = 3, max = 50)
	private String city;
	
	@Column(name = "STATE")
	@Size(min = 3, max = 50)
	private String state;
	
	@Column(name = "ZIP_CODE")
	@Size(min = 3, max = 50)
	private String zipcode;
	

	/**
     * Constructor for ORM.
     */
	public Address(){
		
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	
}

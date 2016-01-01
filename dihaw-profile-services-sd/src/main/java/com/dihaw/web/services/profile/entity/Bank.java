package com.dihaw.web.services.profile.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "BANK") 
public class Bank extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 8134787543214993033L;

	@OneToMany(mappedBy="bank", cascade=CascadeType.ALL)
	@JsonBackReference
	private Collection<Account> accounts;
	
	@Column(name = "BIC")
	@Size(min = 2, max = 8)
	private String bic;				//	Bank Identifier Code (swift)
	
	@ManyToOne(optional = true)
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	
	@Column(name = "NAME")
	@Size(min = 3, max = 50)
	private String name;
	
	@Column(name = "DESCRIPTION")
	@Size(min = 3, max = 50)
	private String description;
	
	/**
     * Constructor for ORM.
     */
	public Bank(){
		
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

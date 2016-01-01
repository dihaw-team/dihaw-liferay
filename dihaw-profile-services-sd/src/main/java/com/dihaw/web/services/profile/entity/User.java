package com.dihaw.web.services.profile.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "USERS") 
public class User extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 2655002720096338127L;
	
	@Column(name = "FIRST_NAME") 
	@NotBlank
	@Size(min = 3, max = 10)
	private String firstName;
	
	@Column(name = "LAST_NAME")
	@NotBlank
	@Size(min = 3, max = 10)
	private String lastName;
	
	@Column(name = "USERNAME")
	@NotBlank
	@Size(min = 3, max = 10)
	private String username;
	
	@NotBlank
	@Size(min = 10, max = 50)
	@Column(name = "EMAIL") 
	private String email;
	
	@Column(name = "PASSWORD")
	@NotBlank
	@Size(min = 5, max = 50)
	private String password;
	
	@Column(name = "LAST_CONNECTION") 
	private Date lastConnection;
	
	@Column(name = "CREATION_DATE") 
	private Date creationDate;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private UserStatus status;					//status: ENABLED, DISABLED, BLOKED or WAITING_FIRST_LOGIN
    
    @Column(name = "ACCOUNT_NON_EXPIRED")
    private int accountNonExpired;				//account not expired
    
    @Column(name = "ACCOUNT_NON_LOKED")
    private int accountNonLocked;				//account non locked
    
    @Column(name = "CREDENTIALS_NON_EXPIRED")
    private int credentialsNonExpired;			//credentials non expired
    
    /**
     * role = 1 ->	ROLE_ADMIN + ROLE_USER	
     * role = 2 ->	ROLE_USER
     * role = 3 ->
     */

//	@OneToOne(cascade={CascadeType.ALL})
//	@JsonBackReference
//	private UserRole userRole;
	
//	@OneToOne(cascade={CascadeType.ALL})
//	@JsonBackReference
//	private Profile profile;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	@JsonBackReference
	private Collection<Account> accounts;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	

	/**
     * Constructor for ORM.
     */
	public User(){
		
	}


    public User(String firstName, String lastName,
    		String username, 
			String email, String password,
			UserStatus status,
			Date creationDate ){
	
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.status = status;
		this.creationDate=creationDate;
	
    }


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getLastConnection() {
		return lastConnection;
	}


	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public UserStatus getStatus() {
		return status;
	}


	public void setStatus(UserStatus status) {
		this.status = status;
	}


	public int getAccountNonExpired() {
		return accountNonExpired;
	}


	public void setAccountNonExpired(int accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}


	public int getAccountNonLocked() {
		return accountNonLocked;
	}


	public void setAccountNonLocked(int accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}


	public int getCredentialsNonExpired() {
		return credentialsNonExpired;
	}


	public void setCredentialsNonExpired(int credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}


//	public UserRole getUserRole() {
//		return userRole;
//	}
//	public void setUserRole(UserRole userRole) {
//		this.userRole = userRole;
//	}


	public Collection<Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


//	public Profile getProfile() {
//		return profile;
//	}
//	public void setProfile(Profile profile) {
//		this.profile = profile;
//	}




}

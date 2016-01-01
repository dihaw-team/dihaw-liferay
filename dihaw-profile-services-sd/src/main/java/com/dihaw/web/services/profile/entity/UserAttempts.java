package com.dihaw.web.services.profile.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "USER_ATTEMPTS")
public class UserAttempts extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1585474756307486533L;

	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "ATTEMPTS")
	private int 	attempts;
	
	@Column(name = "LAST_MODIFIED")
	private Date 	lastModified;
	
	/**
     * constructor for ORM.
     */
	public UserAttempts(){
	}
	
	public UserAttempts(String username, int attempts, Date lastModified){
		this.username = username;
		this.attempts = attempts;
		this.lastModified = lastModified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	

}

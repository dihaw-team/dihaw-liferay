package com.dihaw.web.services.profile.dto;

import java.util.List;

import com.dihaw.web.services.profile.entity.Account;
import com.dihaw.web.services.profile.entity.Bank;
import com.dihaw.web.services.profile.entity.User;

public class ProfileDTO {
	
	private User user;
	private List<Account>	accounts;
	private List<Bank> 		banks;
	private String name;
	private String description;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public List<Bank> getBanks() {
		return banks;
	}
	public void setBanks(List<Bank> banks) {
		this.banks = banks;
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

package com.dihaw.web.services.profile.dto;

import java.math.BigDecimal;

import com.dihaw.web.services.profile.entity.AccountType;

public class AccountDTO {
	
	private String bic;	
	
	private String iban;
	
	private String currency;
	
	private String description;
	
	private BigDecimal amount;
	
	private AccountType type;
	
	private long bankId;
	
	private long userId;

	
	private AccountDTO(){
		
	}
	
	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}

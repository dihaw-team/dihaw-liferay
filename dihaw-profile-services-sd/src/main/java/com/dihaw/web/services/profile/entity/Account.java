package com.dihaw.web.services.profile.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "ACCOUNT") 
public class Account extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -8972329046392873896L;

	@ManyToOne( optional = false )
	@JsonManagedReference
	@JoinColumn(name="BANK_ID")
	private Bank bank;
	
	@ManyToOne( optional = false )
	@JsonManagedReference
	@JoinColumn(name="USER_ID")
	private User user;
	
	/**	
		 *	Votre identifiant 		64026
		 *  Nom complet 			WAHID GAZZAH
		 * 	Nature du compte 		COMPTES CHEQUES PARTICULIERS
		 * 	RIB 					25018000000008360693 
		 * 	IBAN 					TN59 2501 8000 0000 0836 0693
		 * 	Code BIC 				BZITTNTT
			
		 * 	Votre identifiant 		64026
		 * 	Nom complet 			WAHID GAZZAH
		 * 	Nature du compte 		COMPTES TAWFIR
		 * 	RIB 					25018000000009600838
		 * 	IBAN 					TN59 2501 8000 0000 0960 0838
		 * 	Code BIC 				BZITTNTT
	**/
	
	@Column(name = "BIC")
	@Size(min = 2, max = 8)
	private String bic;				//	Bank Identifier Code (swift)
	
	@Column(name = "IBAN")
	@Size(min = 24, max = 24)
	private String iban;			//	International Bank Account Number
	
	@Column(name = "CURRENCY")
	@Size(min = 3, max = 10)
	private String currency;
	
	@Column(name = "DESCRIPTION")
	@NotBlank
	@Size(min = 3, max = 50)
	private String description;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_TYPE")
	private AccountType type;
	
	/**
     * Constructor for ORM.
     */
	public Account(){
		
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	
}

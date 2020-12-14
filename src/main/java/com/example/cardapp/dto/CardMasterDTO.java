package com.example.cardapp.dto;

import java.io.Serializable;

import com.example.cardapp.entities.CustomerMaster;
import com.example.cardapp.enums.CardTypeEnum;

public class CardMasterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private CustomerMaster customer;
	private Long accountId;
	private String cardType;
	transient private String cvc;
	transient private String password;

	/**
	 * @return the cvc
	 */
	public String getCvc() {
		return cvc;
	}

	/**
	 * @param cvc the cvc to set
	 */
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customer
	 */
	public CustomerMaster getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerMaster customer) {
		this.customer = customer;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = CardTypeEnum.getEnumFromName(cardType);
	}

	@Override
	public String toString() {
		return "CardMasterDTO [id=" + id + ", customer=" + customer + ", accountId=" + accountId + ", cardType="
				+ cardType + "]";
	}

}

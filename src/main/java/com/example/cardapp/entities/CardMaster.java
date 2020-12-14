package com.example.cardapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card_master")
public class CardMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "cvc")
    private String cvc;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id",unique = true)
    private CustomerMaster customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public CardMaster accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCardType() {
        return cardType;
    }

    public CardMaster cardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCvc() {
        return cvc;
    }

    public CardMaster cvc(String cvc) {
        this.cvc = cvc;
        return this;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPassword() {
        return password;
    }

    public CardMaster password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerMaster getCustomer() {
        return customer;
    }

    public CardMaster customer(CustomerMaster customerMaster) {
        this.customer = customerMaster;
        return this;
    }

    public void setCustomer(CustomerMaster customerMaster) {
        this.customer = customerMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardMaster)) {
            return false;
        }
        return id != null && id.equals(((CardMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardMaster{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", cardType='" + getCardType() + "'" +
            ", cvc='" + getCvc() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}

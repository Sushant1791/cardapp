package com.example.cardapp.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CustomerMaster.
 */
@Entity
@Table(name = "customer_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "address_1")
	private String address1;

	@Column(name = "address_2")
	private String address2;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public CustomerMaster name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public CustomerMaster address1(String address1) {
		this.address1 = address1;
		return this;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public CustomerMaster address2(String address2) {
		this.address2 = address2;
		return this;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getState() {
		return state;
	}

	public CustomerMaster state(String state) {
		this.state = state;
		return this;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public CustomerMaster city(String city) {
		this.city = city;
		return this;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public CustomerMaster country(String country) {
		this.country = country;
		return this;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CustomerMaster)) {
			return false;
		}
		return id != null && id.equals(((CustomerMaster) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "CustomerMaster{" + "id=" + getId() + ", name='" + getName() + "'" + ", address1='" + getAddress1() + "'"
				+ ", address2='" + getAddress2() + "'" + ", state='" + getState() + "'" + ", city='" + getCity() + "'"
				+ ", country='" + getCountry() + "'" + "}";
	}
}

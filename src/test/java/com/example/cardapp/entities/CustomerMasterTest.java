package com.example.cardapp.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.cardapp.controller.TestUtil;

public class CustomerMasterTest {

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(CustomerMaster.class);
		CustomerMaster customerMaster1 = new CustomerMaster();
		customerMaster1.setId(1L);
		CustomerMaster customerMaster2 = new CustomerMaster();
		customerMaster2.setId(customerMaster1.getId());
		assertThat(customerMaster1).isEqualTo(customerMaster2);
		customerMaster2.setId(2L);
		assertThat(customerMaster1).isNotEqualTo(customerMaster2);
		customerMaster1.setId(null);
		assertThat(customerMaster1).isNotEqualTo(customerMaster2);
	}
}
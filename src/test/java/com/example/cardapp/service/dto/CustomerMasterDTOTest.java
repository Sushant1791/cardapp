package com.example.cardapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.cardapp.controller.TestUtil;
import com.example.cardapp.dto.CustomerMasterDTO;

public class CustomerMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerMasterDTO.class);
        CustomerMasterDTO customerMasterDTO1 = new CustomerMasterDTO();
        customerMasterDTO1.setId(1L);
        CustomerMasterDTO customerMasterDTO2 = new CustomerMasterDTO();
        assertThat(customerMasterDTO1).isNotEqualTo(customerMasterDTO2);
        customerMasterDTO2.setId(customerMasterDTO1.getId());
        assertThat(customerMasterDTO1).isEqualTo(customerMasterDTO2);
        customerMasterDTO2.setId(2L);
        assertThat(customerMasterDTO1).isNotEqualTo(customerMasterDTO2);
        customerMasterDTO1.setId(null);
        assertThat(customerMasterDTO1).isNotEqualTo(customerMasterDTO2);
    }
}

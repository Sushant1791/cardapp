package com.example.cardapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.cardapp.mapper.CustomerMasterMapper;

public class CustomerMasterMapperTest {

    private CustomerMasterMapper customerMasterMapper;

    @BeforeEach
    public void setUp() {
//        customerMasterMapper = new CustomerMasterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerMasterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerMasterMapper.fromId(null)).isNull();
    }
}

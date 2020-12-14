package com.example.cardapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.cardapp.CardAppApplication;
import com.example.cardapp.dto.CustomerMasterDTO;
import com.example.cardapp.entities.CustomerMaster;
import com.example.cardapp.repository.CustomerRepository;

/**
 * Integration tests for the {@link CustomerMasterResource} REST controller.
 */
@SpringBootTest(classes = CardAppApplication.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerControllerTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerMasterRepository;


    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMasterMockMvc;
    
    @Autowired
    private ModelMapper mapper;

    private CustomerMaster customerMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerMaster createEntity(EntityManager em) {
        CustomerMaster customerMaster = new CustomerMaster()
            .name(DEFAULT_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .state(DEFAULT_STATE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        return customerMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerMaster createUpdatedEntity(EntityManager em) {
        CustomerMaster customerMaster = new CustomerMaster()
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        return customerMaster;
    }

    @BeforeEach
    public void initTest() {
        customerMaster = createEntity(em);
    }

    @Test
    @Transactional
    @Order(4)
    public void createCustomerMaster() throws Exception {
        int databaseSizeBeforeCreate = customerMasterRepository.findAll().size();
        // Create the CustomerMaster
        CustomerMasterDTO customerMasterDTO = mapper.map(customerMaster, CustomerMasterDTO.class);
        restCustomerMasterMockMvc.perform(post("/api/customer-masters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerMaster in the database
        List<CustomerMaster> customerMasterList = customerMasterRepository.findAll();
        assertThat(customerMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerMaster testCustomerMaster = customerMasterList.get(customerMasterList.size() - 1);
        assertThat(testCustomerMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerMaster.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCustomerMaster.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCustomerMaster.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCustomerMaster.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerMaster.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    @Order(3)
    public void getAllCustomerMasters() throws Exception {
        // Initialize the database
        customerMasterRepository.saveAndFlush(customerMaster);

        // Get all the customerMasterList
        restCustomerMasterMockMvc.perform(get("/api/customer-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    @Order(1)
    public void getCustomerMaster() throws Exception {
        // Initialize the database
        customerMasterRepository.saveAndFlush(customerMaster);

        // Get the customerMaster
        restCustomerMasterMockMvc.perform(get("/api/customer-masters/{id}", customerMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    @Transactional
    @Order(2)
    public void getNonExistingCustomerMaster() throws Exception {
        // Get the customerMaster
        restCustomerMasterMockMvc.perform(get("/api/customer-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Order(5)
    public void updateCustomerMaster() throws Exception {
        // Initialize the database
        customerMasterRepository.saveAndFlush(customerMaster);

        int databaseSizeBeforeUpdate = customerMasterRepository.findAll().size();

        // Update the customerMaster
        CustomerMaster updatedCustomerMaster = customerMasterRepository.findById(customerMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerMaster are not directly saved in db
        em.detach(updatedCustomerMaster);
        updatedCustomerMaster
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        CustomerMasterDTO customerMasterDTO = mapper.map(customerMaster, CustomerMasterDTO.class);

        restCustomerMasterMockMvc.perform(put("/api/customer-masters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerMasterDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerMaster in the database
        List<CustomerMaster> customerMasterList = customerMasterRepository.findAll();
        assertThat(customerMasterList).hasSize(databaseSizeBeforeUpdate);
        CustomerMaster testCustomerMaster = customerMasterList.get(customerMasterList.size() - 1);
        assertThat(testCustomerMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerMaster.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCustomerMaster.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCustomerMaster.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustomerMaster.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerMaster.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    @Order(8)
    public void deleteCustomerMaster() throws Exception {
        // Initialize the database
        customerMasterRepository.saveAndFlush(customerMaster);

        int databaseSizeBeforeDelete = customerMasterRepository.findAll().size();

        // Delete the customerMaster
        restCustomerMasterMockMvc.perform(delete("/api/customer-masters/{id}", customerMaster.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerMaster> customerMasterList = customerMasterRepository.findAll();
        assertThat(customerMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

}

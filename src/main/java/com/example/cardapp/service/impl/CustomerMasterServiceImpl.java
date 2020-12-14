package com.example.cardapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cardapp.dto.CustomerMasterDTO;
import com.example.cardapp.entities.CustomerMaster;
import com.example.cardapp.repository.CustomerRepository;
import com.example.cardapp.service.CustomerMasterService;

/**
 * Service Implementation for managing {@link CustomerMaster}.
 */
@Service
@Transactional
public class CustomerMasterServiceImpl implements CustomerMasterService {

	private final Logger log = LoggerFactory.getLogger(CustomerMasterServiceImpl.class);

	private final CustomerRepository customerMasterRepository;

	private final ModelMapper mapper;

	public CustomerMasterServiceImpl(CustomerRepository customerMasterRepository, ModelMapper mapper) {
		this.customerMasterRepository = customerMasterRepository;
		this.mapper = mapper;
	}

	@Override
	public CustomerMasterDTO save(CustomerMasterDTO customerMasterDTO) {
		log.debug("Request to save CustomerMaster : {}", customerMasterDTO);
		CustomerMaster customerMaster = mapper.map(customerMasterDTO, CustomerMaster.class);
		customerMaster = customerMasterRepository.save(customerMaster);
		return mapper.map(customerMaster, CustomerMasterDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerMasterDTO> findAll() {
		log.debug("Request to get all CustomerMasters");
		return customerMasterRepository.findAll().stream().map(customer -> {
			return mapper.map(customer, CustomerMasterDTO.class);
		}).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CustomerMasterDTO> findOne(Long id) {
		log.debug("Request to get CustomerMaster : {}", id);
		return customerMasterRepository.findById(id).map(customer -> {
			return mapper.map(customer, CustomerMasterDTO.class);
		});
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete CustomerMaster : {}", id);
		customerMasterRepository.deleteById(id);
	}
}

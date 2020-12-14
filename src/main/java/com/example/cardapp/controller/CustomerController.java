package com.example.cardapp.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapp.dto.CustomerMasterDTO;
import com.example.cardapp.service.CustomerMasterService;
import com.example.cardapp.util.HeaderUtil;
import com.example.cardapp.util.ResponseUtil;

/**
 * REST controller Customers.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

	private final Logger log = LoggerFactory.getLogger(CustomerController.class);

	private static final String SOURCE_NAME = "customerMaster";

	@Value("${card.app.clientName}")
	private String applicationName;

	private final CustomerMasterService customerMasterService;

	public CustomerController(CustomerMasterService customerMasterService) {
		this.customerMasterService = customerMasterService;
	}

	/**
	 * POST RestAPI {/api/customer-masters} which will add customer
	 * 
	 * @param customerMasterDTO
	 * @return
	 * @throws URISyntaxException
	 * @throws BadRequestAlertException
	 */
	@PostMapping("/customer-masters")
	public ResponseEntity<CustomerMasterDTO> createCustomerMaster(@RequestBody CustomerMasterDTO customerMasterDTO)
			throws URISyntaxException, BadRequestAlertException {
		log.debug("REST request to save CustomerMaster : {}", customerMasterDTO);
		if (customerMasterDTO.getId() != null) {
			throw new BadRequestAlertException("A new customerMaster cannot already have an ID", SOURCE_NAME,
					"idexists");
		}
		CustomerMasterDTO result = customerMasterService.save(customerMasterDTO);
		return ResponseEntity
				.created(new URI("/api/customer-masters/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, SOURCE_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /customer-masters} : Updates an existing customerMaster.
	 * 
	 * @param customerMasterDTO
	 * @return
	 * @throws URISyntaxException
	 * @throws BadRequestAlertException
	 */
	@PutMapping("/customer-masters")
	public ResponseEntity<CustomerMasterDTO> updateCustomerMaster(@RequestBody CustomerMasterDTO customerMasterDTO)
			throws URISyntaxException, BadRequestAlertException {
		log.debug("REST request to update CustomerMaster : {}", customerMasterDTO);
		if (customerMasterDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", "Customer Entity", "invalid.customer.id");
		}
		CustomerMasterDTO result = customerMasterService.save(customerMasterDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, SOURCE_NAME,
				customerMasterDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /customer-masters} : get all the customerMasters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of customerMasters in body.
	 */

	/**
	 * {@code GET  /customer-masters} : get all the customerMasters.
	 * 
	 * @return
	 */
	@GetMapping("/customer-masters")
	public List<CustomerMasterDTO> getAllCustomerMasters() {
		log.debug("REST request to get all CustomerMasters");
		return customerMasterService.findAll();
	}

	/**
	 * {@code GET  /customer-masters/:id} : get the "id" customerMaster.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/customer-masters/{id}")
	public ResponseEntity<CustomerMasterDTO> getCustomerMaster(@PathVariable Long id) {
		log.debug("REST request to get CustomerMaster : {}", id);
		Optional<CustomerMasterDTO> customerMasterDTO = customerMasterService.findOne(id);
		return ResponseUtil.wrapOrNotFound(customerMasterDTO);
	}

	/**
	 * {@code DELETE  /customer-masters/:id} : delete the "id" customerMaster.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/customer-masters/{id}")
	public ResponseEntity<Void> deleteCustomerMaster(@PathVariable Long id) {
		log.debug("REST request to delete CustomerMaster : {}", id);
		customerMasterService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, SOURCE_NAME, id.toString()))
				.build();
	}

}

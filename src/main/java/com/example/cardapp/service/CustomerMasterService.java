package com.example.cardapp.service;


import java.util.List;
import java.util.Optional;

import com.example.cardapp.dto.CustomerMasterDTO;

/**
 * Service Interface for managing {@link com.dms.webui.domain.CustomerMaster}.
 */
public interface CustomerMasterService {

    /**
     * Save a customerMaster.
     *
     * @param customerMasterDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerMasterDTO save(CustomerMasterDTO customerMasterDTO);

    /**
     * Get all the customerMasters.
     *
     * @return the list of entities.
     */
    List<CustomerMasterDTO> findAll();


    /**
     * Get the "id" customerMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerMasterDTO> findOne(Long id);

    /**
     * Delete the "id" customerMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

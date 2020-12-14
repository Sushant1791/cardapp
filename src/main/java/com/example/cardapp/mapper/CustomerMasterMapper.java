package com.example.cardapp.mapper;



import org.mapstruct.*;

import com.example.cardapp.dto.CustomerMasterDTO;
import com.example.cardapp.entities.CustomerMaster;

/**
 * Mapper for the entity {@link CustomerMaster} and its DTO {@link CustomerMasterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMasterMapper extends EntityMapper<CustomerMasterDTO, CustomerMaster> {



    default CustomerMaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerMaster customerMaster = new CustomerMaster();
        customerMaster.setId(id);
        return customerMaster;
    }
}

package com.example.cardapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cardapp.entities.CustomerMaster;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerMaster, Long> {

}

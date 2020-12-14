package com.example.cardapp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cardapp.entities.CardMaster;

@Repository
public interface CardRepository extends JpaRepository<CardMaster, Long> {

}

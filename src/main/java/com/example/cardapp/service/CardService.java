package com.example.cardapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.cardapp.dto.CardMasterDTO;

@Service
public interface CardService {

	/**
	 * Save a cardMaster.
	 *
	 * @param cardMasterDTO the entity to save.
	 * @return the persisted entity.
	 */
	CardMasterDTO save(CardMasterDTO cardMasterDTO);

	/**
	 * Get all the cardMasters.
	 *
	 * @return the list of entities.
	 */
	List<CardMasterDTO> findAll();

	/**
	 * Get the "id" cardMaster.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<CardMasterDTO> findOne(Long id);

	/**
	 * Delete the "id" cardMaster.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

}

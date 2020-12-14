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

import com.example.cardapp.dto.CardMasterDTO;
import com.example.cardapp.entities.CardMaster;
import com.example.cardapp.repository.CardRepository;
import com.example.cardapp.service.CardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

	private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

	private final CardRepository cardMasterRepository;

	private final ModelMapper cardMasterMapper;

	public CardServiceImpl(CardRepository cardMasterRepository, ModelMapper cardMasterMapper) {
		this.cardMasterRepository = cardMasterRepository;
		this.cardMasterMapper = cardMasterMapper;
	}

	@Override
	public CardMasterDTO save(CardMasterDTO cardMasterDTO) {
		log.debug("Request to save CardMaster : {}", cardMasterDTO);
		CardMaster cardMaster = cardMasterMapper.map(cardMasterDTO, CardMaster.class);
		cardMaster = cardMasterRepository.save(cardMaster);
		return cardMasterMapper.map(cardMaster, CardMasterDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CardMasterDTO> findAll() {
		log.debug("Request to get all CardMasters");
		return cardMasterRepository.findAll().stream().map(x -> {
			return cardMasterMapper.map(x, CardMasterDTO.class);
		}).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CardMasterDTO> findOne(Long id) {
		log.debug("Request to get CardMaster : {}", id);
		return cardMasterRepository.findById(id).map(x -> {
			return cardMasterMapper.map(x, CardMasterDTO.class);
		});
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete CardMaster : {}", id);
		cardMasterRepository.deleteById(id);
	}

}

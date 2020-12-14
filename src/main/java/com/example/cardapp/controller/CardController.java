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

import com.example.cardapp.dto.CardMasterDTO;
import com.example.cardapp.service.CardService;
import com.example.cardapp.util.HeaderUtil;
import com.example.cardapp.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dms.webui.domain.CardMaster}.
 */
@RestController
@RequestMapping("/api")
public class CardController {

	private final Logger log = LoggerFactory.getLogger(CardController.class);

	private static final String SOURCENAME = "cardMaster";

	@Value("${card.app.clientName}")
	private String applicationName;

	private final CardService cardMasterService;

	public CardController(CardService cardService) {
		this.cardMasterService = cardService;
	}

	/**
	 * {@code POST  /card-masters} : Create a new cardMaster.
	 *
	 * @param cardMasterDTO the cardMasterDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new cardMasterDTO, or with status {@code 400 (Bad Request)}
	 *         if the cardMaster has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/card-masters")
	public ResponseEntity<CardMasterDTO> createCardMaster(@RequestBody CardMasterDTO cardMasterDTO)
			throws URISyntaxException {
		log.debug("REST request to save CardMaster : {}", cardMasterDTO);
		if (cardMasterDTO.getId() != null) {
			throw new BadRequestAlertException("A new cardMaster cannot already have an ID", SOURCENAME, "idexists");
		}
		CardMasterDTO result = cardMasterService.save(cardMasterDTO);
		return ResponseEntity
				.created(new URI("/api/card-masters/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, SOURCENAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /card-masters} : Updates an existing cardMaster.
	 *
	 * @param cardMasterDTO the cardMasterDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated cardMasterDTO, or with status {@code 400 (Bad Request)}
	 *         if the cardMasterDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the cardMasterDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/card-masters")
	public ResponseEntity<CardMasterDTO> updateCardMaster(@RequestBody CardMasterDTO cardMasterDTO)
			throws URISyntaxException {
		log.debug("REST request to update CardMaster : {}", cardMasterDTO);
		if (cardMasterDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", SOURCENAME, "idnull");
		}
		CardMasterDTO result = cardMasterService.save(cardMasterDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, SOURCENAME,
				cardMasterDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /card-masters} : get all the cardMasters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of cardMasters in body.
	 */
	@GetMapping("/card-masters")
	public List<CardMasterDTO> getAllCardMasters() {
		log.debug("REST request to get all CardMasters");
		return cardMasterService.findAll();
	}

	/**
	 * {@code GET  /card-masters/:id} : get the "id" cardMaster.
	 *
	 * @param id the id of the cardMasterDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the cardMasterDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/card-masters/{id}")
	public ResponseEntity<CardMasterDTO> getCardMaster(@PathVariable Long id) {
		log.debug("REST request to get CardMaster : {}", id);
		Optional<CardMasterDTO> cardMasterDTO = cardMasterService.findOne(id);
		return ResponseUtil.wrapOrNotFound(cardMasterDTO);
	}

	/**
	 * {@code DELETE  /card-masters/:id} : delete the "id" cardMaster.
	 *
	 * @param id the id of the cardMasterDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/card-masters/{id}")
	public ResponseEntity<Void> deleteCardMaster(@PathVariable Long id) {
		log.debug("REST request to delete CardMaster : {}", id);
		cardMasterService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, SOURCENAME, id.toString()))
				.build();
	}

}

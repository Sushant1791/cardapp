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
import com.example.cardapp.dto.CardMasterDTO;
import com.example.cardapp.entities.CardMaster;
import com.example.cardapp.repository.CardRepository;

/**
 * Integration tests for the {@link CardMasterResource} REST controller.
 */
@SpringBootTest(classes = CardAppApplication.class)
@AutoConfigureMockMvc
@WithMockUser
public class CardMasterResourceIT {

	private static final Long DEFAULT_ACCOUNT_ID = 1L;
	private static final Long UPDATED_ACCOUNT_ID = 2L;

	private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
	private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

	private static final String DEFAULT_CVC = "AAAAAAAAAA";
	private static final String UPDATED_CVC = "BBBBBBBBBB";

	private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
	private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

	@Autowired
	private CardRepository cardMasterRepository;

	@Autowired
	private ModelMapper cardMasterMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restCardMasterMockMvc;

	private CardMaster cardMaster;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static CardMaster createEntity(EntityManager em) {
		CardMaster cardMaster = new CardMaster().accountId(DEFAULT_ACCOUNT_ID).cardType(DEFAULT_CARD_TYPE)
				.cvc(DEFAULT_CVC).password(DEFAULT_PASSWORD);
		return cardMaster;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static CardMaster createUpdatedEntity(EntityManager em) {
		CardMaster cardMaster = new CardMaster().accountId(UPDATED_ACCOUNT_ID).cardType(UPDATED_CARD_TYPE)
				.cvc(UPDATED_CVC).password(UPDATED_PASSWORD);
		return cardMaster;
	}

	@BeforeEach
	public void initTest() {
		cardMaster = createEntity(em);
	}

	@Test
	@Transactional
	public void createCardMaster() throws Exception {
		int databaseSizeBeforeCreate = cardMasterRepository.findAll().size();
		// Create the CardMaster
		CardMasterDTO cardMasterDTO = cardMasterMapper.map(cardMaster, CardMasterDTO.class);
		restCardMasterMockMvc.perform(post("/api/card-masters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cardMasterDTO))).andExpect(status().isCreated());

		// Validate the CardMaster in the database
		List<CardMaster> cardMasterList = cardMasterRepository.findAll();
		assertThat(cardMasterList).hasSize(databaseSizeBeforeCreate + 1);
		CardMaster testCardMaster = cardMasterList.get(cardMasterList.size() - 1);
		assertThat(testCardMaster.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
		assertThat(testCardMaster.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
		assertThat(testCardMaster.getCvc()).isEqualTo(DEFAULT_CVC);
		assertThat(testCardMaster.getPassword()).isEqualTo(DEFAULT_PASSWORD);
	}

	@Test
	@Transactional
	public void getAllCardMasters() throws Exception {
		// Initialize the database
		cardMasterRepository.saveAndFlush(cardMaster);

		// Get all the cardMasterList
		restCardMasterMockMvc.perform(get("/api/card-masters?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(cardMaster.getId().intValue())))
				.andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
				.andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
				.andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
				.andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
	}

	@Test
	@Transactional
	public void getCardMaster() throws Exception {
		// Initialize the database
		cardMasterRepository.saveAndFlush(cardMaster);

		// Get the cardMaster
		restCardMasterMockMvc.perform(get("/api/card-masters/{id}", cardMaster.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(cardMaster.getId().intValue()))
				.andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.intValue()))
				.andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE))
				.andExpect(jsonPath("$.cvc").value(DEFAULT_CVC))
				.andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
	}

	@Test
	@Transactional
	public void getNonExistingCardMaster() throws Exception {
		// Get the cardMaster
		restCardMasterMockMvc.perform(get("/api/card-masters/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateCardMaster() throws Exception {
		// Initialize the database
		cardMasterRepository.saveAndFlush(cardMaster);

		int databaseSizeBeforeUpdate = cardMasterRepository.findAll().size();

		// Update the cardMaster
		CardMaster updatedCardMaster = cardMasterRepository.findById(cardMaster.getId()).get();
		// Disconnect from session so that the updates on updatedCardMaster are not
		// directly saved in db
		em.detach(updatedCardMaster);
		updatedCardMaster.accountId(UPDATED_ACCOUNT_ID).cardType(UPDATED_CARD_TYPE).cvc(UPDATED_CVC)
				.password(UPDATED_PASSWORD);
		CardMasterDTO cardMasterDTO = cardMasterMapper.map(cardMaster, CardMasterDTO.class);

		restCardMasterMockMvc.perform(put("/api/card-masters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(cardMasterDTO))).andExpect(status().isOk());

		// Validate the CardMaster in the database
		List<CardMaster> cardMasterList = cardMasterRepository.findAll();
		assertThat(cardMasterList).hasSize(databaseSizeBeforeUpdate);
		CardMaster testCardMaster = cardMasterList.get(cardMasterList.size() - 1);
		assertThat(testCardMaster.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
		assertThat(testCardMaster.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
		assertThat(testCardMaster.getCvc()).isEqualTo(UPDATED_CVC);
		assertThat(testCardMaster.getPassword()).isEqualTo(UPDATED_PASSWORD);
	}

	@Test
	@Transactional
	public void deleteCardMaster() throws Exception {
		// Initialize the database
		cardMasterRepository.saveAndFlush(cardMaster);

		int databaseSizeBeforeDelete = cardMasterRepository.findAll().size();

		// Delete the cardMaster
		restCardMasterMockMvc
				.perform(delete("/api/card-masters/{id}", cardMaster.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<CardMaster> cardMasterList = cardMasterRepository.findAll();
		assertThat(cardMasterList).hasSize(databaseSizeBeforeDelete - 1);
	}

}

package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.PersonalAccountingApp;
import mingorance.cano.personal.accounting.domain.AccountInfoType;
import mingorance.cano.personal.accounting.repository.AccountInfoTypeRepository;
import mingorance.cano.personal.accounting.service.AccountInfoTypeService;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoTypeMapper;
import mingorance.cano.personal.accounting.web.rest.errors.ExceptionTranslator;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.AccountInfoTypeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static mingorance.cano.personal.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountInfoTypeResource} REST controller.
 */
@SpringBootTest(classes = PersonalAccountingApp.class)
public class AccountInfoTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final String DEFAULT_CRON = "AAAAAAAAAA";
    private static final String UPDATED_CRON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ONE_TIME = false;
    private static final Boolean UPDATED_IS_ONE_TIME = true;

    @Autowired
    private AccountInfoTypeRepository accountInfoTypeRepository;

    @Autowired
    private AccountInfoTypeMapper accountInfoTypeMapper;

    @Autowired
    private AccountInfoTypeService accountInfoTypeService;

    @Autowired
    private AccountInfoTypeQueryService accountInfoTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAccountInfoTypeMockMvc;

    private AccountInfoType accountInfoType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountInfoTypeResource accountInfoTypeResource = new AccountInfoTypeResource(accountInfoTypeService, accountInfoTypeQueryService);
        this.restAccountInfoTypeMockMvc = MockMvcBuilders.standaloneSetup(accountInfoTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountInfoType createEntity(EntityManager em) {
        AccountInfoType accountInfoType = new AccountInfoType()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID)
            .cron(DEFAULT_CRON)
            .isOneTime(DEFAULT_IS_ONE_TIME);
        return accountInfoType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountInfoType createUpdatedEntity(EntityManager em) {
        AccountInfoType accountInfoType = new AccountInfoType()
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .cron(UPDATED_CRON)
            .isOneTime(UPDATED_IS_ONE_TIME);
        return accountInfoType;
    }

    @BeforeEach
    public void initTest() {
        accountInfoType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountInfoType() throws Exception {
        int databaseSizeBeforeCreate = accountInfoTypeRepository.findAll().size();

        // Create the AccountInfoType
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(accountInfoType);
        restAccountInfoTypeMockMvc.perform(post("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountInfoType in the database
        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AccountInfoType testAccountInfoType = accountInfoTypeList.get(accountInfoTypeList.size() - 1);
        assertThat(testAccountInfoType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAccountInfoType.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAccountInfoType.getCron()).isEqualTo(DEFAULT_CRON);
        assertThat(testAccountInfoType.isIsOneTime()).isEqualTo(DEFAULT_IS_ONE_TIME);
    }

    @Test
    @Transactional
    public void createAccountInfoTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountInfoTypeRepository.findAll().size();

        // Create the AccountInfoType with an existing ID
        accountInfoType.setId(1L);
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(accountInfoType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountInfoTypeMockMvc.perform(post("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfoType in the database
        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoTypeRepository.findAll().size();
        // set the field null
        accountInfoType.setName(null);

        // Create the AccountInfoType, which fails.
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(accountInfoType);

        restAccountInfoTypeMockMvc.perform(post("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoTypeRepository.findAll().size();
        // set the field null
        accountInfoType.setUserId(null);

        // Create the AccountInfoType, which fails.
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(accountInfoType);

        restAccountInfoTypeMockMvc.perform(post("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypes() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountInfoType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].cron").value(hasItem(DEFAULT_CRON)))
            .andExpect(jsonPath("$.[*].isOneTime").value(hasItem(DEFAULT_IS_ONE_TIME.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountInfoType() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get the accountInfoType
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types/{id}", accountInfoType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountInfoType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.cron").value(DEFAULT_CRON))
            .andExpect(jsonPath("$.isOneTime").value(DEFAULT_IS_ONE_TIME.booleanValue()));
    }


    @Test
    @Transactional
    public void getAccountInfoTypesByIdFiltering() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        Long id = accountInfoType.getId();

        defaultAccountInfoTypeShouldBeFound("id.equals=" + id);
        defaultAccountInfoTypeShouldNotBeFound("id.notEquals=" + id);

        defaultAccountInfoTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccountInfoTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultAccountInfoTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccountInfoTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAccountInfoTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name equals to DEFAULT_NAME
        defaultAccountInfoTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the accountInfoTypeList where name equals to UPDATED_NAME
        defaultAccountInfoTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name not equals to DEFAULT_NAME
        defaultAccountInfoTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the accountInfoTypeList where name not equals to UPDATED_NAME
        defaultAccountInfoTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAccountInfoTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the accountInfoTypeList where name equals to UPDATED_NAME
        defaultAccountInfoTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name is not null
        defaultAccountInfoTypeShouldBeFound("name.specified=true");

        // Get all the accountInfoTypeList where name is null
        defaultAccountInfoTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccountInfoTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name contains DEFAULT_NAME
        defaultAccountInfoTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the accountInfoTypeList where name contains UPDATED_NAME
        defaultAccountInfoTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where name does not contain DEFAULT_NAME
        defaultAccountInfoTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the accountInfoTypeList where name does not contain UPDATED_NAME
        defaultAccountInfoTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId equals to DEFAULT_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId equals to UPDATED_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId not equals to DEFAULT_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId not equals to UPDATED_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the accountInfoTypeList where userId equals to UPDATED_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId is not null
        defaultAccountInfoTypeShouldBeFound("userId.specified=true");

        // Get all the accountInfoTypeList where userId is null
        defaultAccountInfoTypeShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId is greater than or equal to DEFAULT_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId is greater than or equal to UPDATED_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId is less than or equal to DEFAULT_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId is less than or equal to SMALLER_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId is less than DEFAULT_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId is less than UPDATED_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where userId is greater than DEFAULT_USER_ID
        defaultAccountInfoTypeShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the accountInfoTypeList where userId is greater than SMALLER_USER_ID
        defaultAccountInfoTypeShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllAccountInfoTypesByCronIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron equals to DEFAULT_CRON
        defaultAccountInfoTypeShouldBeFound("cron.equals=" + DEFAULT_CRON);

        // Get all the accountInfoTypeList where cron equals to UPDATED_CRON
        defaultAccountInfoTypeShouldNotBeFound("cron.equals=" + UPDATED_CRON);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByCronIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron not equals to DEFAULT_CRON
        defaultAccountInfoTypeShouldNotBeFound("cron.notEquals=" + DEFAULT_CRON);

        // Get all the accountInfoTypeList where cron not equals to UPDATED_CRON
        defaultAccountInfoTypeShouldBeFound("cron.notEquals=" + UPDATED_CRON);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByCronIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron in DEFAULT_CRON or UPDATED_CRON
        defaultAccountInfoTypeShouldBeFound("cron.in=" + DEFAULT_CRON + "," + UPDATED_CRON);

        // Get all the accountInfoTypeList where cron equals to UPDATED_CRON
        defaultAccountInfoTypeShouldNotBeFound("cron.in=" + UPDATED_CRON);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByCronIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron is not null
        defaultAccountInfoTypeShouldBeFound("cron.specified=true");

        // Get all the accountInfoTypeList where cron is null
        defaultAccountInfoTypeShouldNotBeFound("cron.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccountInfoTypesByCronContainsSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron contains DEFAULT_CRON
        defaultAccountInfoTypeShouldBeFound("cron.contains=" + DEFAULT_CRON);

        // Get all the accountInfoTypeList where cron contains UPDATED_CRON
        defaultAccountInfoTypeShouldNotBeFound("cron.contains=" + UPDATED_CRON);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByCronNotContainsSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where cron does not contain DEFAULT_CRON
        defaultAccountInfoTypeShouldNotBeFound("cron.doesNotContain=" + DEFAULT_CRON);

        // Get all the accountInfoTypeList where cron does not contain UPDATED_CRON
        defaultAccountInfoTypeShouldBeFound("cron.doesNotContain=" + UPDATED_CRON);
    }


    @Test
    @Transactional
    public void getAllAccountInfoTypesByIsOneTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where isOneTime equals to DEFAULT_IS_ONE_TIME
        defaultAccountInfoTypeShouldBeFound("isOneTime.equals=" + DEFAULT_IS_ONE_TIME);

        // Get all the accountInfoTypeList where isOneTime equals to UPDATED_IS_ONE_TIME
        defaultAccountInfoTypeShouldNotBeFound("isOneTime.equals=" + UPDATED_IS_ONE_TIME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByIsOneTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where isOneTime not equals to DEFAULT_IS_ONE_TIME
        defaultAccountInfoTypeShouldNotBeFound("isOneTime.notEquals=" + DEFAULT_IS_ONE_TIME);

        // Get all the accountInfoTypeList where isOneTime not equals to UPDATED_IS_ONE_TIME
        defaultAccountInfoTypeShouldBeFound("isOneTime.notEquals=" + UPDATED_IS_ONE_TIME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByIsOneTimeIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where isOneTime in DEFAULT_IS_ONE_TIME or UPDATED_IS_ONE_TIME
        defaultAccountInfoTypeShouldBeFound("isOneTime.in=" + DEFAULT_IS_ONE_TIME + "," + UPDATED_IS_ONE_TIME);

        // Get all the accountInfoTypeList where isOneTime equals to UPDATED_IS_ONE_TIME
        defaultAccountInfoTypeShouldNotBeFound("isOneTime.in=" + UPDATED_IS_ONE_TIME);
    }

    @Test
    @Transactional
    public void getAllAccountInfoTypesByIsOneTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        // Get all the accountInfoTypeList where isOneTime is not null
        defaultAccountInfoTypeShouldBeFound("isOneTime.specified=true");

        // Get all the accountInfoTypeList where isOneTime is null
        defaultAccountInfoTypeShouldNotBeFound("isOneTime.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountInfoTypeShouldBeFound(String filter) throws Exception {
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountInfoType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].cron").value(hasItem(DEFAULT_CRON)))
            .andExpect(jsonPath("$.[*].isOneTime").value(hasItem(DEFAULT_IS_ONE_TIME.booleanValue())));

        // Check, that the count call also returns 1
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountInfoTypeShouldNotBeFound(String filter) throws Exception {
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAccountInfoType() throws Exception {
        // Get the accountInfoType
        restAccountInfoTypeMockMvc.perform(get("/api/account-info-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountInfoType() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        int databaseSizeBeforeUpdate = accountInfoTypeRepository.findAll().size();

        // Update the accountInfoType
        AccountInfoType updatedAccountInfoType = accountInfoTypeRepository.findById(accountInfoType.getId()).get();
        // Disconnect from session so that the updates on updatedAccountInfoType are not directly saved in db
        em.detach(updatedAccountInfoType);
        updatedAccountInfoType
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .cron(UPDATED_CRON)
            .isOneTime(UPDATED_IS_ONE_TIME);
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(updatedAccountInfoType);

        restAccountInfoTypeMockMvc.perform(put("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AccountInfoType in the database
        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeUpdate);
        AccountInfoType testAccountInfoType = accountInfoTypeList.get(accountInfoTypeList.size() - 1);
        assertThat(testAccountInfoType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAccountInfoType.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAccountInfoType.getCron()).isEqualTo(UPDATED_CRON);
        assertThat(testAccountInfoType.isIsOneTime()).isEqualTo(UPDATED_IS_ONE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountInfoType() throws Exception {
        int databaseSizeBeforeUpdate = accountInfoTypeRepository.findAll().size();

        // Create the AccountInfoType
        AccountInfoTypeDTO accountInfoTypeDTO = accountInfoTypeMapper.toDto(accountInfoType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountInfoTypeMockMvc.perform(put("/api/account-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfoType in the database
        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountInfoType() throws Exception {
        // Initialize the database
        accountInfoTypeRepository.saveAndFlush(accountInfoType);

        int databaseSizeBeforeDelete = accountInfoTypeRepository.findAll().size();

        // Delete the accountInfoType
        restAccountInfoTypeMockMvc.perform(delete("/api/account-info-types/{id}", accountInfoType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountInfoType> accountInfoTypeList = accountInfoTypeRepository.findAll();
        assertThat(accountInfoTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

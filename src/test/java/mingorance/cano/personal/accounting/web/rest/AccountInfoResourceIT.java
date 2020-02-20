package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.PersonalAccountingApp;
import mingorance.cano.personal.accounting.domain.AccountInfo;
import mingorance.cano.personal.accounting.domain.AccountInfoType;
import mingorance.cano.personal.accounting.repository.AccountInfoRepository;
import mingorance.cano.personal.accounting.service.AccountInfoService;
import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoMapper;
import mingorance.cano.personal.accounting.web.rest.errors.ExceptionTranslator;
import mingorance.cano.personal.accounting.service.dto.AccountInfoCriteria;
import mingorance.cano.personal.accounting.service.AccountInfoQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mingorance.cano.personal.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountInfoResource} REST controller.
 */
@SpringBootTest(classes = PersonalAccountingApp.class)
public class AccountInfoResourceIT {

    private static final String DEFAULT_CONCEPT = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPT = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final LocalDate DEFAULT_STARTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STARTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ENDING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ENDING_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private AccountInfoQueryService accountInfoQueryService;

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

    private MockMvc restAccountInfoMockMvc;

    private AccountInfo accountInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountInfoResource accountInfoResource = new AccountInfoResource(accountInfoService, accountInfoQueryService);
        this.restAccountInfoMockMvc = MockMvcBuilders.standaloneSetup(accountInfoResource)
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
    public static AccountInfo createEntity(EntityManager em) {
        AccountInfo accountInfo = new AccountInfo()
            .concept(DEFAULT_CONCEPT)
            .userId(DEFAULT_USER_ID)
            .startingDate(DEFAULT_STARTING_DATE)
            .endingDate(DEFAULT_ENDING_DATE);
        // Add required entity
        AccountInfoType accountInfoType;
        if (TestUtil.findAll(em, AccountInfoType.class).isEmpty()) {
            accountInfoType = AccountInfoTypeResourceIT.createEntity(em);
            em.persist(accountInfoType);
            em.flush();
        } else {
            accountInfoType = TestUtil.findAll(em, AccountInfoType.class).get(0);
        }
        accountInfo.setType(accountInfoType);
        return accountInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountInfo createUpdatedEntity(EntityManager em) {
        AccountInfo accountInfo = new AccountInfo()
            .concept(UPDATED_CONCEPT)
            .userId(UPDATED_USER_ID)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE);
        // Add required entity
        AccountInfoType accountInfoType;
        if (TestUtil.findAll(em, AccountInfoType.class).isEmpty()) {
            accountInfoType = AccountInfoTypeResourceIT.createUpdatedEntity(em);
            em.persist(accountInfoType);
            em.flush();
        } else {
            accountInfoType = TestUtil.findAll(em, AccountInfoType.class).get(0);
        }
        accountInfo.setType(accountInfoType);
        return accountInfo;
    }

    @BeforeEach
    public void initTest() {
        accountInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountInfo() throws Exception {
        int databaseSizeBeforeCreate = accountInfoRepository.findAll().size();

        // Create the AccountInfo
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);
        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AccountInfo testAccountInfo = accountInfoList.get(accountInfoList.size() - 1);
        assertThat(testAccountInfo.getConcept()).isEqualTo(DEFAULT_CONCEPT);
        assertThat(testAccountInfo.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAccountInfo.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testAccountInfo.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
    }

    @Test
    @Transactional
    public void createAccountInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountInfoRepository.findAll().size();

        // Create the AccountInfo with an existing ID
        accountInfo.setId(1L);
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConceptIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoRepository.findAll().size();
        // set the field null
        accountInfo.setConcept(null);

        // Create the AccountInfo, which fails.
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoRepository.findAll().size();
        // set the field null
        accountInfo.setUserId(null);

        // Create the AccountInfo, which fails.
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoRepository.findAll().size();
        // set the field null
        accountInfo.setStartingDate(null);

        // Create the AccountInfo, which fails.
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountInfos() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList
        restAccountInfoMockMvc.perform(get("/api/account-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get the accountInfo
        restAccountInfoMockMvc.perform(get("/api/account-infos/{id}", accountInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountInfo.getId().intValue()))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.startingDate").value(DEFAULT_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAccountInfosByIdFiltering() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        Long id = accountInfo.getId();

        defaultAccountInfoShouldBeFound("id.equals=" + id);
        defaultAccountInfoShouldNotBeFound("id.notEquals=" + id);

        defaultAccountInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccountInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultAccountInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccountInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAccountInfosByConceptIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept equals to DEFAULT_CONCEPT
        defaultAccountInfoShouldBeFound("concept.equals=" + DEFAULT_CONCEPT);

        // Get all the accountInfoList where concept equals to UPDATED_CONCEPT
        defaultAccountInfoShouldNotBeFound("concept.equals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByConceptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept not equals to DEFAULT_CONCEPT
        defaultAccountInfoShouldNotBeFound("concept.notEquals=" + DEFAULT_CONCEPT);

        // Get all the accountInfoList where concept not equals to UPDATED_CONCEPT
        defaultAccountInfoShouldBeFound("concept.notEquals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByConceptIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept in DEFAULT_CONCEPT or UPDATED_CONCEPT
        defaultAccountInfoShouldBeFound("concept.in=" + DEFAULT_CONCEPT + "," + UPDATED_CONCEPT);

        // Get all the accountInfoList where concept equals to UPDATED_CONCEPT
        defaultAccountInfoShouldNotBeFound("concept.in=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByConceptIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept is not null
        defaultAccountInfoShouldBeFound("concept.specified=true");

        // Get all the accountInfoList where concept is null
        defaultAccountInfoShouldNotBeFound("concept.specified=false");
    }
                @Test
    @Transactional
    public void getAllAccountInfosByConceptContainsSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept contains DEFAULT_CONCEPT
        defaultAccountInfoShouldBeFound("concept.contains=" + DEFAULT_CONCEPT);

        // Get all the accountInfoList where concept contains UPDATED_CONCEPT
        defaultAccountInfoShouldNotBeFound("concept.contains=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByConceptNotContainsSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where concept does not contain DEFAULT_CONCEPT
        defaultAccountInfoShouldNotBeFound("concept.doesNotContain=" + DEFAULT_CONCEPT);

        // Get all the accountInfoList where concept does not contain UPDATED_CONCEPT
        defaultAccountInfoShouldBeFound("concept.doesNotContain=" + UPDATED_CONCEPT);
    }


    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId equals to DEFAULT_USER_ID
        defaultAccountInfoShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId equals to UPDATED_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId not equals to DEFAULT_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId not equals to UPDATED_USER_ID
        defaultAccountInfoShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultAccountInfoShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the accountInfoList where userId equals to UPDATED_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId is not null
        defaultAccountInfoShouldBeFound("userId.specified=true");

        // Get all the accountInfoList where userId is null
        defaultAccountInfoShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId is greater than or equal to DEFAULT_USER_ID
        defaultAccountInfoShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId is greater than or equal to UPDATED_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId is less than or equal to DEFAULT_USER_ID
        defaultAccountInfoShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId is less than or equal to SMALLER_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId is less than DEFAULT_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId is less than UPDATED_USER_ID
        defaultAccountInfoShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where userId is greater than DEFAULT_USER_ID
        defaultAccountInfoShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the accountInfoList where userId is greater than SMALLER_USER_ID
        defaultAccountInfoShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate equals to DEFAULT_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.equals=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate equals to UPDATED_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.equals=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate not equals to DEFAULT_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.notEquals=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate not equals to UPDATED_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.notEquals=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate in DEFAULT_STARTING_DATE or UPDATED_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.in=" + DEFAULT_STARTING_DATE + "," + UPDATED_STARTING_DATE);

        // Get all the accountInfoList where startingDate equals to UPDATED_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.in=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate is not null
        defaultAccountInfoShouldBeFound("startingDate.specified=true");

        // Get all the accountInfoList where startingDate is null
        defaultAccountInfoShouldNotBeFound("startingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate is greater than or equal to DEFAULT_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.greaterThanOrEqual=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate is greater than or equal to UPDATED_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.greaterThanOrEqual=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate is less than or equal to DEFAULT_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.lessThanOrEqual=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate is less than or equal to SMALLER_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.lessThanOrEqual=" + SMALLER_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate is less than DEFAULT_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.lessThan=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate is less than UPDATED_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.lessThan=" + UPDATED_STARTING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByStartingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where startingDate is greater than DEFAULT_STARTING_DATE
        defaultAccountInfoShouldNotBeFound("startingDate.greaterThan=" + DEFAULT_STARTING_DATE);

        // Get all the accountInfoList where startingDate is greater than SMALLER_STARTING_DATE
        defaultAccountInfoShouldBeFound("startingDate.greaterThan=" + SMALLER_STARTING_DATE);
    }


    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate equals to DEFAULT_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.equals=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate equals to UPDATED_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.equals=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate not equals to DEFAULT_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.notEquals=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate not equals to UPDATED_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.notEquals=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsInShouldWork() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate in DEFAULT_ENDING_DATE or UPDATED_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.in=" + DEFAULT_ENDING_DATE + "," + UPDATED_ENDING_DATE);

        // Get all the accountInfoList where endingDate equals to UPDATED_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.in=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate is not null
        defaultAccountInfoShouldBeFound("endingDate.specified=true");

        // Get all the accountInfoList where endingDate is null
        defaultAccountInfoShouldNotBeFound("endingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate is greater than or equal to DEFAULT_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.greaterThanOrEqual=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate is greater than or equal to UPDATED_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.greaterThanOrEqual=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate is less than or equal to DEFAULT_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.lessThanOrEqual=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate is less than or equal to SMALLER_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.lessThanOrEqual=" + SMALLER_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate is less than DEFAULT_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.lessThan=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate is less than UPDATED_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.lessThan=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountInfosByEndingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        // Get all the accountInfoList where endingDate is greater than DEFAULT_ENDING_DATE
        defaultAccountInfoShouldNotBeFound("endingDate.greaterThan=" + DEFAULT_ENDING_DATE);

        // Get all the accountInfoList where endingDate is greater than SMALLER_ENDING_DATE
        defaultAccountInfoShouldBeFound("endingDate.greaterThan=" + SMALLER_ENDING_DATE);
    }


    @Test
    @Transactional
    public void getAllAccountInfosByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        AccountInfoType type = accountInfo.getType();
        accountInfoRepository.saveAndFlush(accountInfo);
        Long typeId = type.getId();

        // Get all the accountInfoList where type equals to typeId
        defaultAccountInfoShouldBeFound("typeId.equals=" + typeId);

        // Get all the accountInfoList where type equals to typeId + 1
        defaultAccountInfoShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountInfoShouldBeFound(String filter) throws Exception {
        restAccountInfoMockMvc.perform(get("/api/account-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())));

        // Check, that the count call also returns 1
        restAccountInfoMockMvc.perform(get("/api/account-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountInfoShouldNotBeFound(String filter) throws Exception {
        restAccountInfoMockMvc.perform(get("/api/account-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountInfoMockMvc.perform(get("/api/account-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAccountInfo() throws Exception {
        // Get the accountInfo
        restAccountInfoMockMvc.perform(get("/api/account-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        int databaseSizeBeforeUpdate = accountInfoRepository.findAll().size();

        // Update the accountInfo
        AccountInfo updatedAccountInfo = accountInfoRepository.findById(accountInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAccountInfo are not directly saved in db
        em.detach(updatedAccountInfo);
        updatedAccountInfo
            .concept(UPDATED_CONCEPT)
            .userId(UPDATED_USER_ID)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE);
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(updatedAccountInfo);

        restAccountInfoMockMvc.perform(put("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isOk());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeUpdate);
        AccountInfo testAccountInfo = accountInfoList.get(accountInfoList.size() - 1);
        assertThat(testAccountInfo.getConcept()).isEqualTo(UPDATED_CONCEPT);
        assertThat(testAccountInfo.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAccountInfo.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testAccountInfo.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountInfo() throws Exception {
        int databaseSizeBeforeUpdate = accountInfoRepository.findAll().size();

        // Create the AccountInfo
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountInfoMockMvc.perform(put("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.saveAndFlush(accountInfo);

        int databaseSizeBeforeDelete = accountInfoRepository.findAll().size();

        // Delete the accountInfo
        restAccountInfoMockMvc.perform(delete("/api/account-infos/{id}", accountInfo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

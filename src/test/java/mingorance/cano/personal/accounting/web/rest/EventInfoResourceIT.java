package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.PersonalAccountingApp;
import mingorance.cano.personal.accounting.domain.EventInfo;
import mingorance.cano.personal.accounting.domain.Calendar;
import mingorance.cano.personal.accounting.domain.EventInfoType;
import mingorance.cano.personal.accounting.repository.EventInfoRepository;
import mingorance.cano.personal.accounting.service.EventInfoService;
import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoMapper;
import mingorance.cano.personal.accounting.web.rest.errors.ExceptionTranslator;
import mingorance.cano.personal.accounting.service.dto.EventInfoCriteria;
import mingorance.cano.personal.accounting.service.EventInfoQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mingorance.cano.personal.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mingorance.cano.personal.accounting.domain.enumeration.AmountType;
/**
 * Integration tests for the {@link EventInfoResource} REST controller.
 */
@SpringBootTest(classes = PersonalAccountingApp.class)
public class EventInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final AmountType DEFAULT_AMOUNT_TYPE = AmountType.SUM;
    private static final AmountType UPDATED_AMOUNT_TYPE = AmountType.FIX;

    private static final String DEFAULT_ITERATE_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ITERATE_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_COLOUR = "AAAAAAAAAA";
    private static final String UPDATED_COLOUR = "BBBBBBBBBB";

    @Autowired
    private EventInfoRepository eventInfoRepository;

    @Autowired
    private EventInfoMapper eventInfoMapper;

    @Autowired
    private EventInfoService eventInfoService;

    @Autowired
    private EventInfoQueryService eventInfoQueryService;

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

    private MockMvc restEventInfoMockMvc;

    private EventInfo eventInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventInfoResource eventInfoResource = new EventInfoResource(eventInfoService, eventInfoQueryService);
        this.restEventInfoMockMvc = MockMvcBuilders.standaloneSetup(eventInfoResource)
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
    public static EventInfo createEntity(EntityManager em) {
        EventInfo eventInfo = new EventInfo()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT)
            .amountType(DEFAULT_AMOUNT_TYPE)
            .iterateInformation(DEFAULT_ITERATE_INFORMATION)
            .colour(DEFAULT_COLOUR);
        // Add required entity
        Calendar calendar;
        if (TestUtil.findAll(em, Calendar.class).isEmpty()) {
            calendar = CalendarResourceIT.createEntity(em);
            em.persist(calendar);
            em.flush();
        } else {
            calendar = TestUtil.findAll(em, Calendar.class).get(0);
        }
        eventInfo.setCalendar(calendar);
        // Add required entity
        EventInfoType eventInfoType;
        if (TestUtil.findAll(em, EventInfoType.class).isEmpty()) {
            eventInfoType = EventInfoTypeResourceIT.createEntity(em);
            em.persist(eventInfoType);
            em.flush();
        } else {
            eventInfoType = TestUtil.findAll(em, EventInfoType.class).get(0);
        }
        eventInfo.setType(eventInfoType);
        return eventInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventInfo createUpdatedEntity(EntityManager em) {
        EventInfo eventInfo = new EventInfo()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .amountType(UPDATED_AMOUNT_TYPE)
            .iterateInformation(UPDATED_ITERATE_INFORMATION)
            .colour(UPDATED_COLOUR);
        // Add required entity
        Calendar calendar;
        if (TestUtil.findAll(em, Calendar.class).isEmpty()) {
            calendar = CalendarResourceIT.createUpdatedEntity(em);
            em.persist(calendar);
            em.flush();
        } else {
            calendar = TestUtil.findAll(em, Calendar.class).get(0);
        }
        eventInfo.setCalendar(calendar);
        // Add required entity
        EventInfoType eventInfoType;
        if (TestUtil.findAll(em, EventInfoType.class).isEmpty()) {
            eventInfoType = EventInfoTypeResourceIT.createUpdatedEntity(em);
            em.persist(eventInfoType);
            em.flush();
        } else {
            eventInfoType = TestUtil.findAll(em, EventInfoType.class).get(0);
        }
        eventInfo.setType(eventInfoType);
        return eventInfo;
    }

    @BeforeEach
    public void initTest() {
        eventInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventInfo() throws Exception {
        int databaseSizeBeforeCreate = eventInfoRepository.findAll().size();

        // Create the EventInfo
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);
        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the EventInfo in the database
        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EventInfo testEventInfo = eventInfoList.get(eventInfoList.size() - 1);
        assertThat(testEventInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventInfo.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEventInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEventInfo.getAmountType()).isEqualTo(DEFAULT_AMOUNT_TYPE);
        assertThat(testEventInfo.getIterateInformation()).isEqualTo(DEFAULT_ITERATE_INFORMATION);
        assertThat(testEventInfo.getColour()).isEqualTo(DEFAULT_COLOUR);
    }

    @Test
    @Transactional
    public void createEventInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventInfoRepository.findAll().size();

        // Create the EventInfo with an existing ID
        eventInfo.setId(1L);
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventInfo in the database
        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoRepository.findAll().size();
        // set the field null
        eventInfo.setName(null);

        // Create the EventInfo, which fails.
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoRepository.findAll().size();
        // set the field null
        eventInfo.setDate(null);

        // Create the EventInfo, which fails.
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoRepository.findAll().size();
        // set the field null
        eventInfo.setAmount(null);

        // Create the EventInfo, which fails.
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoRepository.findAll().size();
        // set the field null
        eventInfo.setAmountType(null);

        // Create the EventInfo, which fails.
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColourIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoRepository.findAll().size();
        // set the field null
        eventInfo.setColour(null);

        // Create the EventInfo, which fails.
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        restEventInfoMockMvc.perform(post("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventInfos() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList
        restEventInfoMockMvc.perform(get("/api/event-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amountType").value(hasItem(DEFAULT_AMOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].iterateInformation").value(hasItem(DEFAULT_ITERATE_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)));
    }
    
    @Test
    @Transactional
    public void getEventInfo() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get the eventInfo
        restEventInfoMockMvc.perform(get("/api/event-infos/{id}", eventInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.amountType").value(DEFAULT_AMOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.iterateInformation").value(DEFAULT_ITERATE_INFORMATION.toString()))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR));
    }


    @Test
    @Transactional
    public void getEventInfosByIdFiltering() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        Long id = eventInfo.getId();

        defaultEventInfoShouldBeFound("id.equals=" + id);
        defaultEventInfoShouldNotBeFound("id.notEquals=" + id);

        defaultEventInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultEventInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEventInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name equals to DEFAULT_NAME
        defaultEventInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the eventInfoList where name equals to UPDATED_NAME
        defaultEventInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name not equals to DEFAULT_NAME
        defaultEventInfoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the eventInfoList where name not equals to UPDATED_NAME
        defaultEventInfoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEventInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the eventInfoList where name equals to UPDATED_NAME
        defaultEventInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name is not null
        defaultEventInfoShouldBeFound("name.specified=true");

        // Get all the eventInfoList where name is null
        defaultEventInfoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllEventInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name contains DEFAULT_NAME
        defaultEventInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the eventInfoList where name contains UPDATED_NAME
        defaultEventInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where name does not contain DEFAULT_NAME
        defaultEventInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the eventInfoList where name does not contain UPDATED_NAME
        defaultEventInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllEventInfosByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date equals to DEFAULT_DATE
        defaultEventInfoShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the eventInfoList where date equals to UPDATED_DATE
        defaultEventInfoShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date not equals to DEFAULT_DATE
        defaultEventInfoShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the eventInfoList where date not equals to UPDATED_DATE
        defaultEventInfoShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEventInfoShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the eventInfoList where date equals to UPDATED_DATE
        defaultEventInfoShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date is not null
        defaultEventInfoShouldBeFound("date.specified=true");

        // Get all the eventInfoList where date is null
        defaultEventInfoShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date is greater than or equal to DEFAULT_DATE
        defaultEventInfoShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventInfoList where date is greater than or equal to UPDATED_DATE
        defaultEventInfoShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date is less than or equal to DEFAULT_DATE
        defaultEventInfoShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventInfoList where date is less than or equal to SMALLER_DATE
        defaultEventInfoShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date is less than DEFAULT_DATE
        defaultEventInfoShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the eventInfoList where date is less than UPDATED_DATE
        defaultEventInfoShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where date is greater than DEFAULT_DATE
        defaultEventInfoShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the eventInfoList where date is greater than SMALLER_DATE
        defaultEventInfoShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllEventInfosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount equals to DEFAULT_AMOUNT
        defaultEventInfoShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount equals to UPDATED_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount not equals to DEFAULT_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount not equals to UPDATED_AMOUNT
        defaultEventInfoShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultEventInfoShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the eventInfoList where amount equals to UPDATED_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount is not null
        defaultEventInfoShouldBeFound("amount.specified=true");

        // Get all the eventInfoList where amount is null
        defaultEventInfoShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultEventInfoShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount is greater than or equal to UPDATED_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount is less than or equal to DEFAULT_AMOUNT
        defaultEventInfoShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount is less than or equal to SMALLER_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount is less than DEFAULT_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount is less than UPDATED_AMOUNT
        defaultEventInfoShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amount is greater than DEFAULT_AMOUNT
        defaultEventInfoShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the eventInfoList where amount is greater than SMALLER_AMOUNT
        defaultEventInfoShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllEventInfosByAmountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amountType equals to DEFAULT_AMOUNT_TYPE
        defaultEventInfoShouldBeFound("amountType.equals=" + DEFAULT_AMOUNT_TYPE);

        // Get all the eventInfoList where amountType equals to UPDATED_AMOUNT_TYPE
        defaultEventInfoShouldNotBeFound("amountType.equals=" + UPDATED_AMOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amountType not equals to DEFAULT_AMOUNT_TYPE
        defaultEventInfoShouldNotBeFound("amountType.notEquals=" + DEFAULT_AMOUNT_TYPE);

        // Get all the eventInfoList where amountType not equals to UPDATED_AMOUNT_TYPE
        defaultEventInfoShouldBeFound("amountType.notEquals=" + UPDATED_AMOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amountType in DEFAULT_AMOUNT_TYPE or UPDATED_AMOUNT_TYPE
        defaultEventInfoShouldBeFound("amountType.in=" + DEFAULT_AMOUNT_TYPE + "," + UPDATED_AMOUNT_TYPE);

        // Get all the eventInfoList where amountType equals to UPDATED_AMOUNT_TYPE
        defaultEventInfoShouldNotBeFound("amountType.in=" + UPDATED_AMOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEventInfosByAmountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where amountType is not null
        defaultEventInfoShouldBeFound("amountType.specified=true");

        // Get all the eventInfoList where amountType is null
        defaultEventInfoShouldNotBeFound("amountType.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventInfosByColourIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour equals to DEFAULT_COLOUR
        defaultEventInfoShouldBeFound("colour.equals=" + DEFAULT_COLOUR);

        // Get all the eventInfoList where colour equals to UPDATED_COLOUR
        defaultEventInfoShouldNotBeFound("colour.equals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllEventInfosByColourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour not equals to DEFAULT_COLOUR
        defaultEventInfoShouldNotBeFound("colour.notEquals=" + DEFAULT_COLOUR);

        // Get all the eventInfoList where colour not equals to UPDATED_COLOUR
        defaultEventInfoShouldBeFound("colour.notEquals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllEventInfosByColourIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour in DEFAULT_COLOUR or UPDATED_COLOUR
        defaultEventInfoShouldBeFound("colour.in=" + DEFAULT_COLOUR + "," + UPDATED_COLOUR);

        // Get all the eventInfoList where colour equals to UPDATED_COLOUR
        defaultEventInfoShouldNotBeFound("colour.in=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllEventInfosByColourIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour is not null
        defaultEventInfoShouldBeFound("colour.specified=true");

        // Get all the eventInfoList where colour is null
        defaultEventInfoShouldNotBeFound("colour.specified=false");
    }
                @Test
    @Transactional
    public void getAllEventInfosByColourContainsSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour contains DEFAULT_COLOUR
        defaultEventInfoShouldBeFound("colour.contains=" + DEFAULT_COLOUR);

        // Get all the eventInfoList where colour contains UPDATED_COLOUR
        defaultEventInfoShouldNotBeFound("colour.contains=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllEventInfosByColourNotContainsSomething() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        // Get all the eventInfoList where colour does not contain DEFAULT_COLOUR
        defaultEventInfoShouldNotBeFound("colour.doesNotContain=" + DEFAULT_COLOUR);

        // Get all the eventInfoList where colour does not contain UPDATED_COLOUR
        defaultEventInfoShouldBeFound("colour.doesNotContain=" + UPDATED_COLOUR);
    }


    @Test
    @Transactional
    public void getAllEventInfosByCalendarIsEqualToSomething() throws Exception {
        // Get already existing entity
        Calendar calendar = eventInfo.getCalendar();
        eventInfoRepository.saveAndFlush(eventInfo);
        Long calendarId = calendar.getId();

        // Get all the eventInfoList where calendar equals to calendarId
        defaultEventInfoShouldBeFound("calendarId.equals=" + calendarId);

        // Get all the eventInfoList where calendar equals to calendarId + 1
        defaultEventInfoShouldNotBeFound("calendarId.equals=" + (calendarId + 1));
    }


    @Test
    @Transactional
    public void getAllEventInfosByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        EventInfoType type = eventInfo.getType();
        eventInfoRepository.saveAndFlush(eventInfo);
        Long typeId = type.getId();

        // Get all the eventInfoList where type equals to typeId
        defaultEventInfoShouldBeFound("typeId.equals=" + typeId);

        // Get all the eventInfoList where type equals to typeId + 1
        defaultEventInfoShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventInfoShouldBeFound(String filter) throws Exception {
        restEventInfoMockMvc.perform(get("/api/event-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amountType").value(hasItem(DEFAULT_AMOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].iterateInformation").value(hasItem(DEFAULT_ITERATE_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)));

        // Check, that the count call also returns 1
        restEventInfoMockMvc.perform(get("/api/event-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventInfoShouldNotBeFound(String filter) throws Exception {
        restEventInfoMockMvc.perform(get("/api/event-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventInfoMockMvc.perform(get("/api/event-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEventInfo() throws Exception {
        // Get the eventInfo
        restEventInfoMockMvc.perform(get("/api/event-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventInfo() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        int databaseSizeBeforeUpdate = eventInfoRepository.findAll().size();

        // Update the eventInfo
        EventInfo updatedEventInfo = eventInfoRepository.findById(eventInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEventInfo are not directly saved in db
        em.detach(updatedEventInfo);
        updatedEventInfo
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .amountType(UPDATED_AMOUNT_TYPE)
            .iterateInformation(UPDATED_ITERATE_INFORMATION)
            .colour(UPDATED_COLOUR);
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(updatedEventInfo);

        restEventInfoMockMvc.perform(put("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isOk());

        // Validate the EventInfo in the database
        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeUpdate);
        EventInfo testEventInfo = eventInfoList.get(eventInfoList.size() - 1);
        assertThat(testEventInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventInfo.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEventInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEventInfo.getAmountType()).isEqualTo(UPDATED_AMOUNT_TYPE);
        assertThat(testEventInfo.getIterateInformation()).isEqualTo(UPDATED_ITERATE_INFORMATION);
        assertThat(testEventInfo.getColour()).isEqualTo(UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingEventInfo() throws Exception {
        int databaseSizeBeforeUpdate = eventInfoRepository.findAll().size();

        // Create the EventInfo
        EventInfoDTO eventInfoDTO = eventInfoMapper.toDto(eventInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventInfoMockMvc.perform(put("/api/event-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventInfo in the database
        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventInfo() throws Exception {
        // Initialize the database
        eventInfoRepository.saveAndFlush(eventInfo);

        int databaseSizeBeforeDelete = eventInfoRepository.findAll().size();

        // Delete the eventInfo
        restEventInfoMockMvc.perform(delete("/api/event-infos/{id}", eventInfo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventInfo> eventInfoList = eventInfoRepository.findAll();
        assertThat(eventInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

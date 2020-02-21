package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.PersonalAccountingApp;
import mingorance.cano.personal.accounting.domain.Calendar;
import mingorance.cano.personal.accounting.domain.User;
import mingorance.cano.personal.accounting.repository.CalendarRepository;
import mingorance.cano.personal.accounting.service.CalendarService;
import mingorance.cano.personal.accounting.service.dto.CalendarDTO;
import mingorance.cano.personal.accounting.service.mapper.CalendarMapper;
import mingorance.cano.personal.accounting.web.rest.errors.ExceptionTranslator;
import mingorance.cano.personal.accounting.service.dto.CalendarCriteria;
import mingorance.cano.personal.accounting.service.CalendarQueryService;

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
 * Integration tests for the {@link CalendarResource} REST controller.
 */
@SpringBootTest(classes = PersonalAccountingApp.class)
public class CalendarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOUR = "AAAAAAAAAA";
    private static final String UPDATED_COLOUR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private CalendarMapper calendarMapper;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarQueryService calendarQueryService;

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

    private MockMvc restCalendarMockMvc;

    private Calendar calendar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarResource calendarResource = new CalendarResource(calendarService, calendarQueryService);
        this.restCalendarMockMvc = MockMvcBuilders.standaloneSetup(calendarResource)
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
    public static Calendar createEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .name(DEFAULT_NAME)
            .colour(DEFAULT_COLOUR)
            .description(DEFAULT_DESCRIPTION)
            .timeZone(DEFAULT_TIME_ZONE)
            .enabled(DEFAULT_ENABLED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        calendar.setUser(user);
        return calendar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createUpdatedEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .name(UPDATED_NAME)
            .colour(UPDATED_COLOUR)
            .description(UPDATED_DESCRIPTION)
            .timeZone(UPDATED_TIME_ZONE)
            .enabled(UPDATED_ENABLED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        calendar.setUser(user);
        return calendar;
    }

    @BeforeEach
    public void initTest() {
        calendar = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendar() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isCreated());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate + 1);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCalendar.getColour()).isEqualTo(DEFAULT_COLOUR);
        assertThat(testCalendar.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCalendar.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testCalendar.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createCalendarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar with an existing ID
        calendar.setId(1L);
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setName(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColourIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setColour(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeZoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setTimeZone(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setEnabled(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendars() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", calendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }


    @Test
    @Transactional
    public void getCalendarsByIdFiltering() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        Long id = calendar.getId();

        defaultCalendarShouldBeFound("id.equals=" + id);
        defaultCalendarShouldNotBeFound("id.notEquals=" + id);

        defaultCalendarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCalendarShouldNotBeFound("id.greaterThan=" + id);

        defaultCalendarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCalendarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCalendarsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name equals to DEFAULT_NAME
        defaultCalendarShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the calendarList where name equals to UPDATED_NAME
        defaultCalendarShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name not equals to DEFAULT_NAME
        defaultCalendarShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the calendarList where name not equals to UPDATED_NAME
        defaultCalendarShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCalendarShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the calendarList where name equals to UPDATED_NAME
        defaultCalendarShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name is not null
        defaultCalendarShouldBeFound("name.specified=true");

        // Get all the calendarList where name is null
        defaultCalendarShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCalendarsByNameContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name contains DEFAULT_NAME
        defaultCalendarShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the calendarList where name contains UPDATED_NAME
        defaultCalendarShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where name does not contain DEFAULT_NAME
        defaultCalendarShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the calendarList where name does not contain UPDATED_NAME
        defaultCalendarShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCalendarsByColourIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour equals to DEFAULT_COLOUR
        defaultCalendarShouldBeFound("colour.equals=" + DEFAULT_COLOUR);

        // Get all the calendarList where colour equals to UPDATED_COLOUR
        defaultCalendarShouldNotBeFound("colour.equals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllCalendarsByColourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour not equals to DEFAULT_COLOUR
        defaultCalendarShouldNotBeFound("colour.notEquals=" + DEFAULT_COLOUR);

        // Get all the calendarList where colour not equals to UPDATED_COLOUR
        defaultCalendarShouldBeFound("colour.notEquals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllCalendarsByColourIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour in DEFAULT_COLOUR or UPDATED_COLOUR
        defaultCalendarShouldBeFound("colour.in=" + DEFAULT_COLOUR + "," + UPDATED_COLOUR);

        // Get all the calendarList where colour equals to UPDATED_COLOUR
        defaultCalendarShouldNotBeFound("colour.in=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllCalendarsByColourIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour is not null
        defaultCalendarShouldBeFound("colour.specified=true");

        // Get all the calendarList where colour is null
        defaultCalendarShouldNotBeFound("colour.specified=false");
    }
                @Test
    @Transactional
    public void getAllCalendarsByColourContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour contains DEFAULT_COLOUR
        defaultCalendarShouldBeFound("colour.contains=" + DEFAULT_COLOUR);

        // Get all the calendarList where colour contains UPDATED_COLOUR
        defaultCalendarShouldNotBeFound("colour.contains=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllCalendarsByColourNotContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where colour does not contain DEFAULT_COLOUR
        defaultCalendarShouldNotBeFound("colour.doesNotContain=" + DEFAULT_COLOUR);

        // Get all the calendarList where colour does not contain UPDATED_COLOUR
        defaultCalendarShouldBeFound("colour.doesNotContain=" + UPDATED_COLOUR);
    }


    @Test
    @Transactional
    public void getAllCalendarsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description equals to DEFAULT_DESCRIPTION
        defaultCalendarShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the calendarList where description equals to UPDATED_DESCRIPTION
        defaultCalendarShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description not equals to DEFAULT_DESCRIPTION
        defaultCalendarShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the calendarList where description not equals to UPDATED_DESCRIPTION
        defaultCalendarShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCalendarShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the calendarList where description equals to UPDATED_DESCRIPTION
        defaultCalendarShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description is not null
        defaultCalendarShouldBeFound("description.specified=true");

        // Get all the calendarList where description is null
        defaultCalendarShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCalendarsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description contains DEFAULT_DESCRIPTION
        defaultCalendarShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the calendarList where description contains UPDATED_DESCRIPTION
        defaultCalendarShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where description does not contain DEFAULT_DESCRIPTION
        defaultCalendarShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the calendarList where description does not contain UPDATED_DESCRIPTION
        defaultCalendarShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCalendarsByTimeZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone equals to DEFAULT_TIME_ZONE
        defaultCalendarShouldBeFound("timeZone.equals=" + DEFAULT_TIME_ZONE);

        // Get all the calendarList where timeZone equals to UPDATED_TIME_ZONE
        defaultCalendarShouldNotBeFound("timeZone.equals=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeZoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone not equals to DEFAULT_TIME_ZONE
        defaultCalendarShouldNotBeFound("timeZone.notEquals=" + DEFAULT_TIME_ZONE);

        // Get all the calendarList where timeZone not equals to UPDATED_TIME_ZONE
        defaultCalendarShouldBeFound("timeZone.notEquals=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeZoneIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone in DEFAULT_TIME_ZONE or UPDATED_TIME_ZONE
        defaultCalendarShouldBeFound("timeZone.in=" + DEFAULT_TIME_ZONE + "," + UPDATED_TIME_ZONE);

        // Get all the calendarList where timeZone equals to UPDATED_TIME_ZONE
        defaultCalendarShouldNotBeFound("timeZone.in=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeZoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone is not null
        defaultCalendarShouldBeFound("timeZone.specified=true");

        // Get all the calendarList where timeZone is null
        defaultCalendarShouldNotBeFound("timeZone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCalendarsByTimeZoneContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone contains DEFAULT_TIME_ZONE
        defaultCalendarShouldBeFound("timeZone.contains=" + DEFAULT_TIME_ZONE);

        // Get all the calendarList where timeZone contains UPDATED_TIME_ZONE
        defaultCalendarShouldNotBeFound("timeZone.contains=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeZoneNotContainsSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeZone does not contain DEFAULT_TIME_ZONE
        defaultCalendarShouldNotBeFound("timeZone.doesNotContain=" + DEFAULT_TIME_ZONE);

        // Get all the calendarList where timeZone does not contain UPDATED_TIME_ZONE
        defaultCalendarShouldBeFound("timeZone.doesNotContain=" + UPDATED_TIME_ZONE);
    }


    @Test
    @Transactional
    public void getAllCalendarsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where enabled equals to DEFAULT_ENABLED
        defaultCalendarShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the calendarList where enabled equals to UPDATED_ENABLED
        defaultCalendarShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCalendarsByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where enabled not equals to DEFAULT_ENABLED
        defaultCalendarShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the calendarList where enabled not equals to UPDATED_ENABLED
        defaultCalendarShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCalendarsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultCalendarShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the calendarList where enabled equals to UPDATED_ENABLED
        defaultCalendarShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCalendarsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where enabled is not null
        defaultCalendarShouldBeFound("enabled.specified=true");

        // Get all the calendarList where enabled is null
        defaultCalendarShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = calendar.getUser();
        calendarRepository.saveAndFlush(calendar);
        Long userId = user.getId();

        // Get all the calendarList where user equals to userId
        defaultCalendarShouldBeFound("userId.equals=" + userId);

        // Get all the calendarList where user equals to userId + 1
        defaultCalendarShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCalendarShouldBeFound(String filter) throws Exception {
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restCalendarMockMvc.perform(get("/api/calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCalendarShouldNotBeFound(String filter) throws Exception {
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCalendarMockMvc.perform(get("/api/calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCalendar() throws Exception {
        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar
        Calendar updatedCalendar = calendarRepository.findById(calendar.getId()).get();
        // Disconnect from session so that the updates on updatedCalendar are not directly saved in db
        em.detach(updatedCalendar);
        updatedCalendar
            .name(UPDATED_NAME)
            .colour(UPDATED_COLOUR)
            .description(UPDATED_DESCRIPTION)
            .timeZone(UPDATED_TIME_ZONE)
            .enabled(UPDATED_ENABLED);
        CalendarDTO calendarDTO = calendarMapper.toDto(updatedCalendar);

        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCalendar.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testCalendar.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCalendar.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testCalendar.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeDelete = calendarRepository.findAll().size();

        // Delete the calendar
        restCalendarMockMvc.perform(delete("/api/calendars/{id}", calendar.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.PersonalAccountingApp;
import mingorance.cano.personal.accounting.domain.EventInfoType;
import mingorance.cano.personal.accounting.domain.User;
import mingorance.cano.personal.accounting.repository.EventInfoTypeRepository;
import mingorance.cano.personal.accounting.service.EventInfoTypeService;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoTypeMapper;
import mingorance.cano.personal.accounting.web.rest.errors.ExceptionTranslator;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.EventInfoTypeQueryService;

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
 * Integration tests for the {@link EventInfoTypeResource} REST controller.
 */
@SpringBootTest(classes = PersonalAccountingApp.class)
public class EventInfoTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private EventInfoTypeRepository eventInfoTypeRepository;

    @Autowired
    private EventInfoTypeMapper eventInfoTypeMapper;

    @Autowired
    private EventInfoTypeService eventInfoTypeService;

    @Autowired
    private EventInfoTypeQueryService eventInfoTypeQueryService;

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

    private MockMvc restEventInfoTypeMockMvc;

    private EventInfoType eventInfoType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventInfoTypeResource eventInfoTypeResource = new EventInfoTypeResource(eventInfoTypeService, eventInfoTypeQueryService);
        this.restEventInfoTypeMockMvc = MockMvcBuilders.standaloneSetup(eventInfoTypeResource)
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
    public static EventInfoType createEntity(EntityManager em) {
        EventInfoType eventInfoType = new EventInfoType()
            .name(DEFAULT_NAME)
            .icon(DEFAULT_ICON);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        eventInfoType.setUser(user);
        return eventInfoType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventInfoType createUpdatedEntity(EntityManager em) {
        EventInfoType eventInfoType = new EventInfoType()
            .name(UPDATED_NAME)
            .icon(UPDATED_ICON);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        eventInfoType.setUser(user);
        return eventInfoType;
    }

    @BeforeEach
    public void initTest() {
        eventInfoType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventInfoType() throws Exception {
        int databaseSizeBeforeCreate = eventInfoTypeRepository.findAll().size();

        // Create the EventInfoType
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(eventInfoType);
        restEventInfoTypeMockMvc.perform(post("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EventInfoType in the database
        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EventInfoType testEventInfoType = eventInfoTypeList.get(eventInfoTypeList.size() - 1);
        assertThat(testEventInfoType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventInfoType.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createEventInfoTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventInfoTypeRepository.findAll().size();

        // Create the EventInfoType with an existing ID
        eventInfoType.setId(1L);
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(eventInfoType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventInfoTypeMockMvc.perform(post("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventInfoType in the database
        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoTypeRepository.findAll().size();
        // set the field null
        eventInfoType.setName(null);

        // Create the EventInfoType, which fails.
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(eventInfoType);

        restEventInfoTypeMockMvc.perform(post("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventInfoTypeRepository.findAll().size();
        // set the field null
        eventInfoType.setIcon(null);

        // Create the EventInfoType, which fails.
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(eventInfoType);

        restEventInfoTypeMockMvc.perform(post("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypes() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventInfoType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getEventInfoType() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get the eventInfoType
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types/{id}", eventInfoType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventInfoType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }


    @Test
    @Transactional
    public void getEventInfoTypesByIdFiltering() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        Long id = eventInfoType.getId();

        defaultEventInfoTypeShouldBeFound("id.equals=" + id);
        defaultEventInfoTypeShouldNotBeFound("id.notEquals=" + id);

        defaultEventInfoTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventInfoTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultEventInfoTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventInfoTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEventInfoTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name equals to DEFAULT_NAME
        defaultEventInfoTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the eventInfoTypeList where name equals to UPDATED_NAME
        defaultEventInfoTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name not equals to DEFAULT_NAME
        defaultEventInfoTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the eventInfoTypeList where name not equals to UPDATED_NAME
        defaultEventInfoTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEventInfoTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the eventInfoTypeList where name equals to UPDATED_NAME
        defaultEventInfoTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name is not null
        defaultEventInfoTypeShouldBeFound("name.specified=true");

        // Get all the eventInfoTypeList where name is null
        defaultEventInfoTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllEventInfoTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name contains DEFAULT_NAME
        defaultEventInfoTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the eventInfoTypeList where name contains UPDATED_NAME
        defaultEventInfoTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where name does not contain DEFAULT_NAME
        defaultEventInfoTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the eventInfoTypeList where name does not contain UPDATED_NAME
        defaultEventInfoTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllEventInfoTypesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon equals to DEFAULT_ICON
        defaultEventInfoTypeShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the eventInfoTypeList where icon equals to UPDATED_ICON
        defaultEventInfoTypeShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon not equals to DEFAULT_ICON
        defaultEventInfoTypeShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the eventInfoTypeList where icon not equals to UPDATED_ICON
        defaultEventInfoTypeShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultEventInfoTypeShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the eventInfoTypeList where icon equals to UPDATED_ICON
        defaultEventInfoTypeShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon is not null
        defaultEventInfoTypeShouldBeFound("icon.specified=true");

        // Get all the eventInfoTypeList where icon is null
        defaultEventInfoTypeShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllEventInfoTypesByIconContainsSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon contains DEFAULT_ICON
        defaultEventInfoTypeShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the eventInfoTypeList where icon contains UPDATED_ICON
        defaultEventInfoTypeShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllEventInfoTypesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        // Get all the eventInfoTypeList where icon does not contain DEFAULT_ICON
        defaultEventInfoTypeShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the eventInfoTypeList where icon does not contain UPDATED_ICON
        defaultEventInfoTypeShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllEventInfoTypesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = eventInfoType.getUser();
        eventInfoTypeRepository.saveAndFlush(eventInfoType);
        Long userId = user.getId();

        // Get all the eventInfoTypeList where user equals to userId
        defaultEventInfoTypeShouldBeFound("userId.equals=" + userId);

        // Get all the eventInfoTypeList where user equals to userId + 1
        defaultEventInfoTypeShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventInfoTypeShouldBeFound(String filter) throws Exception {
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventInfoType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));

        // Check, that the count call also returns 1
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventInfoTypeShouldNotBeFound(String filter) throws Exception {
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEventInfoType() throws Exception {
        // Get the eventInfoType
        restEventInfoTypeMockMvc.perform(get("/api/event-info-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventInfoType() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        int databaseSizeBeforeUpdate = eventInfoTypeRepository.findAll().size();

        // Update the eventInfoType
        EventInfoType updatedEventInfoType = eventInfoTypeRepository.findById(eventInfoType.getId()).get();
        // Disconnect from session so that the updates on updatedEventInfoType are not directly saved in db
        em.detach(updatedEventInfoType);
        updatedEventInfoType
            .name(UPDATED_NAME)
            .icon(UPDATED_ICON);
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(updatedEventInfoType);

        restEventInfoTypeMockMvc.perform(put("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isOk());

        // Validate the EventInfoType in the database
        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeUpdate);
        EventInfoType testEventInfoType = eventInfoTypeList.get(eventInfoTypeList.size() - 1);
        assertThat(testEventInfoType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventInfoType.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingEventInfoType() throws Exception {
        int databaseSizeBeforeUpdate = eventInfoTypeRepository.findAll().size();

        // Create the EventInfoType
        EventInfoTypeDTO eventInfoTypeDTO = eventInfoTypeMapper.toDto(eventInfoType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventInfoTypeMockMvc.perform(put("/api/event-info-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventInfoTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventInfoType in the database
        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventInfoType() throws Exception {
        // Initialize the database
        eventInfoTypeRepository.saveAndFlush(eventInfoType);

        int databaseSizeBeforeDelete = eventInfoTypeRepository.findAll().size();

        // Delete the eventInfoType
        restEventInfoTypeMockMvc.perform(delete("/api/event-info-types/{id}", eventInfoType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventInfoType> eventInfoTypeList = eventInfoTypeRepository.findAll();
        assertThat(eventInfoTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

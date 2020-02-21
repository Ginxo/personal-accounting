package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.service.EventInfoTypeService;
import mingorance.cano.personal.accounting.web.rest.errors.BadRequestAlertException;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.EventInfoTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link mingorance.cano.personal.accounting.domain.EventInfoType}.
 */
@RestController
@RequestMapping("/api")
public class EventInfoTypeResource {

    private final Logger log = LoggerFactory.getLogger(EventInfoTypeResource.class);

    private static final String ENTITY_NAME = "eventInfoType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventInfoTypeService eventInfoTypeService;

    private final EventInfoTypeQueryService eventInfoTypeQueryService;

    public EventInfoTypeResource(EventInfoTypeService eventInfoTypeService, EventInfoTypeQueryService eventInfoTypeQueryService) {
        this.eventInfoTypeService = eventInfoTypeService;
        this.eventInfoTypeQueryService = eventInfoTypeQueryService;
    }

    /**
     * {@code POST  /event-info-types} : Create a new eventInfoType.
     *
     * @param eventInfoTypeDTO the eventInfoTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventInfoTypeDTO, or with status {@code 400 (Bad Request)} if the eventInfoType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-info-types")
    public ResponseEntity<EventInfoTypeDTO> createEventInfoType(@Valid @RequestBody EventInfoTypeDTO eventInfoTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EventInfoType : {}", eventInfoTypeDTO);
        if (eventInfoTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventInfoType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventInfoTypeDTO result = eventInfoTypeService.save(eventInfoTypeDTO);
        return ResponseEntity.created(new URI("/api/event-info-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-info-types} : Updates an existing eventInfoType.
     *
     * @param eventInfoTypeDTO the eventInfoTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventInfoTypeDTO,
     * or with status {@code 400 (Bad Request)} if the eventInfoTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventInfoTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-info-types")
    public ResponseEntity<EventInfoTypeDTO> updateEventInfoType(@Valid @RequestBody EventInfoTypeDTO eventInfoTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EventInfoType : {}", eventInfoTypeDTO);
        if (eventInfoTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventInfoTypeDTO result = eventInfoTypeService.save(eventInfoTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventInfoTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-info-types} : get all the eventInfoTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventInfoTypes in body.
     */
    @GetMapping("/event-info-types")
    public ResponseEntity<List<EventInfoTypeDTO>> getAllEventInfoTypes(EventInfoTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EventInfoTypes by criteria: {}", criteria);
        Page<EventInfoTypeDTO> page = eventInfoTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-info-types/count} : count all the eventInfoTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-info-types/count")
    public ResponseEntity<Long> countEventInfoTypes(EventInfoTypeCriteria criteria) {
        log.debug("REST request to count EventInfoTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventInfoTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-info-types/:id} : get the "id" eventInfoType.
     *
     * @param id the id of the eventInfoTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventInfoTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-info-types/{id}")
    public ResponseEntity<EventInfoTypeDTO> getEventInfoType(@PathVariable Long id) {
        log.debug("REST request to get EventInfoType : {}", id);
        Optional<EventInfoTypeDTO> eventInfoTypeDTO = eventInfoTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventInfoTypeDTO);
    }

    /**
     * {@code DELETE  /event-info-types/:id} : delete the "id" eventInfoType.
     *
     * @param id the id of the eventInfoTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-info-types/{id}")
    public ResponseEntity<Void> deleteEventInfoType(@PathVariable Long id) {
        log.debug("REST request to delete EventInfoType : {}", id);
        eventInfoTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.service.EventInfoService;
import mingorance.cano.personal.accounting.web.rest.errors.BadRequestAlertException;
import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;
import mingorance.cano.personal.accounting.service.dto.EventInfoCriteria;
import mingorance.cano.personal.accounting.service.EventInfoQueryService;

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
 * REST controller for managing {@link mingorance.cano.personal.accounting.domain.EventInfo}.
 */
@RestController
@RequestMapping("/api")
public class EventInfoResource {

    private final Logger log = LoggerFactory.getLogger(EventInfoResource.class);

    private static final String ENTITY_NAME = "eventInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventInfoService eventInfoService;

    private final EventInfoQueryService eventInfoQueryService;

    public EventInfoResource(EventInfoService eventInfoService, EventInfoQueryService eventInfoQueryService) {
        this.eventInfoService = eventInfoService;
        this.eventInfoQueryService = eventInfoQueryService;
    }

    /**
     * {@code POST  /event-infos} : Create a new eventInfo.
     *
     * @param eventInfoDTO the eventInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventInfoDTO, or with status {@code 400 (Bad Request)} if the eventInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-infos")
    public ResponseEntity<EventInfoDTO> createEventInfo(@Valid @RequestBody EventInfoDTO eventInfoDTO) throws URISyntaxException {
        log.debug("REST request to save EventInfo : {}", eventInfoDTO);
        if (eventInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventInfoDTO result = eventInfoService.save(eventInfoDTO);
        return ResponseEntity.created(new URI("/api/event-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-infos} : Updates an existing eventInfo.
     *
     * @param eventInfoDTO the eventInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventInfoDTO,
     * or with status {@code 400 (Bad Request)} if the eventInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-infos")
    public ResponseEntity<EventInfoDTO> updateEventInfo(@Valid @RequestBody EventInfoDTO eventInfoDTO) throws URISyntaxException {
        log.debug("REST request to update EventInfo : {}", eventInfoDTO);
        if (eventInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventInfoDTO result = eventInfoService.save(eventInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-infos} : get all the eventInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventInfos in body.
     */
    @GetMapping("/event-infos")
    public ResponseEntity<List<EventInfoDTO>> getAllEventInfos(EventInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EventInfos by criteria: {}", criteria);
        Page<EventInfoDTO> page = eventInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-infos/count} : count all the eventInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-infos/count")
    public ResponseEntity<Long> countEventInfos(EventInfoCriteria criteria) {
        log.debug("REST request to count EventInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-infos/:id} : get the "id" eventInfo.
     *
     * @param id the id of the eventInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-infos/{id}")
    public ResponseEntity<EventInfoDTO> getEventInfo(@PathVariable Long id) {
        log.debug("REST request to get EventInfo : {}", id);
        Optional<EventInfoDTO> eventInfoDTO = eventInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventInfoDTO);
    }

    /**
     * {@code DELETE  /event-infos/:id} : delete the "id" eventInfo.
     *
     * @param id the id of the eventInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-infos/{id}")
    public ResponseEntity<Void> deleteEventInfo(@PathVariable Long id) {
        log.debug("REST request to delete EventInfo : {}", id);
        eventInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

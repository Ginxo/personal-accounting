package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.service.AccountInfoTypeService;
import mingorance.cano.personal.accounting.web.rest.errors.BadRequestAlertException;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.AccountInfoTypeQueryService;

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
 * REST controller for managing {@link mingorance.cano.personal.accounting.domain.AccountInfoType}.
 */
@RestController
@RequestMapping("/api")
public class AccountInfoTypeResource {

    private final Logger log = LoggerFactory.getLogger(AccountInfoTypeResource.class);

    private static final String ENTITY_NAME = "accountInfoType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountInfoTypeService accountInfoTypeService;

    private final AccountInfoTypeQueryService accountInfoTypeQueryService;

    public AccountInfoTypeResource(AccountInfoTypeService accountInfoTypeService, AccountInfoTypeQueryService accountInfoTypeQueryService) {
        this.accountInfoTypeService = accountInfoTypeService;
        this.accountInfoTypeQueryService = accountInfoTypeQueryService;
    }

    /**
     * {@code POST  /account-info-types} : Create a new accountInfoType.
     *
     * @param accountInfoTypeDTO the accountInfoTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountInfoTypeDTO, or with status {@code 400 (Bad Request)} if the accountInfoType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-info-types")
    public ResponseEntity<AccountInfoTypeDTO> createAccountInfoType(@Valid @RequestBody AccountInfoTypeDTO accountInfoTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AccountInfoType : {}", accountInfoTypeDTO);
        if (accountInfoTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountInfoType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountInfoTypeDTO result = accountInfoTypeService.save(accountInfoTypeDTO);
        return ResponseEntity.created(new URI("/api/account-info-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-info-types} : Updates an existing accountInfoType.
     *
     * @param accountInfoTypeDTO the accountInfoTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountInfoTypeDTO,
     * or with status {@code 400 (Bad Request)} if the accountInfoTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountInfoTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-info-types")
    public ResponseEntity<AccountInfoTypeDTO> updateAccountInfoType(@Valid @RequestBody AccountInfoTypeDTO accountInfoTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AccountInfoType : {}", accountInfoTypeDTO);
        if (accountInfoTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountInfoTypeDTO result = accountInfoTypeService.save(accountInfoTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountInfoTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-info-types} : get all the accountInfoTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountInfoTypes in body.
     */
    @GetMapping("/account-info-types")
    public ResponseEntity<List<AccountInfoTypeDTO>> getAllAccountInfoTypes(AccountInfoTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AccountInfoTypes by criteria: {}", criteria);
        Page<AccountInfoTypeDTO> page = accountInfoTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-info-types/count} : count all the accountInfoTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/account-info-types/count")
    public ResponseEntity<Long> countAccountInfoTypes(AccountInfoTypeCriteria criteria) {
        log.debug("REST request to count AccountInfoTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(accountInfoTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /account-info-types/:id} : get the "id" accountInfoType.
     *
     * @param id the id of the accountInfoTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountInfoTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-info-types/{id}")
    public ResponseEntity<AccountInfoTypeDTO> getAccountInfoType(@PathVariable Long id) {
        log.debug("REST request to get AccountInfoType : {}", id);
        Optional<AccountInfoTypeDTO> accountInfoTypeDTO = accountInfoTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountInfoTypeDTO);
    }

    /**
     * {@code DELETE  /account-info-types/:id} : delete the "id" accountInfoType.
     *
     * @param id the id of the accountInfoTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-info-types/{id}")
    public ResponseEntity<Void> deleteAccountInfoType(@PathVariable Long id) {
        log.debug("REST request to delete AccountInfoType : {}", id);
        accountInfoTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package mingorance.cano.personal.accounting.web.rest;

import mingorance.cano.personal.accounting.service.AccountInfoService;
import mingorance.cano.personal.accounting.web.rest.errors.BadRequestAlertException;
import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;
import mingorance.cano.personal.accounting.service.dto.AccountInfoCriteria;
import mingorance.cano.personal.accounting.service.AccountInfoQueryService;

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
 * REST controller for managing {@link mingorance.cano.personal.accounting.domain.AccountInfo}.
 */
@RestController
@RequestMapping("/api")
public class AccountInfoResource {

    private final Logger log = LoggerFactory.getLogger(AccountInfoResource.class);

    private static final String ENTITY_NAME = "accountInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountInfoService accountInfoService;

    private final AccountInfoQueryService accountInfoQueryService;

    public AccountInfoResource(AccountInfoService accountInfoService, AccountInfoQueryService accountInfoQueryService) {
        this.accountInfoService = accountInfoService;
        this.accountInfoQueryService = accountInfoQueryService;
    }

    /**
     * {@code POST  /account-infos} : Create a new accountInfo.
     *
     * @param accountInfoDTO the accountInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountInfoDTO, or with status {@code 400 (Bad Request)} if the accountInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-infos")
    public ResponseEntity<AccountInfoDTO> createAccountInfo(@Valid @RequestBody AccountInfoDTO accountInfoDTO) throws URISyntaxException {
        log.debug("REST request to save AccountInfo : {}", accountInfoDTO);
        if (accountInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountInfoDTO result = accountInfoService.save(accountInfoDTO);
        return ResponseEntity.created(new URI("/api/account-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-infos} : Updates an existing accountInfo.
     *
     * @param accountInfoDTO the accountInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountInfoDTO,
     * or with status {@code 400 (Bad Request)} if the accountInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-infos")
    public ResponseEntity<AccountInfoDTO> updateAccountInfo(@Valid @RequestBody AccountInfoDTO accountInfoDTO) throws URISyntaxException {
        log.debug("REST request to update AccountInfo : {}", accountInfoDTO);
        if (accountInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountInfoDTO result = accountInfoService.save(accountInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-infos} : get all the accountInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountInfos in body.
     */
    @GetMapping("/account-infos")
    public ResponseEntity<List<AccountInfoDTO>> getAllAccountInfos(AccountInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AccountInfos by criteria: {}", criteria);
        Page<AccountInfoDTO> page = accountInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-infos/count} : count all the accountInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/account-infos/count")
    public ResponseEntity<Long> countAccountInfos(AccountInfoCriteria criteria) {
        log.debug("REST request to count AccountInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(accountInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /account-infos/:id} : get the "id" accountInfo.
     *
     * @param id the id of the accountInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-infos/{id}")
    public ResponseEntity<AccountInfoDTO> getAccountInfo(@PathVariable Long id) {
        log.debug("REST request to get AccountInfo : {}", id);
        Optional<AccountInfoDTO> accountInfoDTO = accountInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountInfoDTO);
    }

    /**
     * {@code DELETE  /account-infos/:id} : delete the "id" accountInfo.
     *
     * @param id the id of the accountInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-infos/{id}")
    public ResponseEntity<Void> deleteAccountInfo(@PathVariable Long id) {
        log.debug("REST request to delete AccountInfo : {}", id);
        accountInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package mingorance.cano.personal.accounting.service;

import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mingorance.cano.personal.accounting.domain.AccountInfo}.
 */
public interface AccountInfoService {

    /**
     * Save a accountInfo.
     *
     * @param accountInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AccountInfoDTO save(AccountInfoDTO accountInfoDTO);

    /**
     * Get all the accountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accountInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountInfoDTO> findOne(Long id);

    /**
     * Delete the "id" accountInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

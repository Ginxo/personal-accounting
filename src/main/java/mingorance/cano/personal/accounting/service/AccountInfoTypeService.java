package mingorance.cano.personal.accounting.service;

import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mingorance.cano.personal.accounting.domain.AccountInfoType}.
 */
public interface AccountInfoTypeService {

    /**
     * Save a accountInfoType.
     *
     * @param accountInfoTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AccountInfoTypeDTO save(AccountInfoTypeDTO accountInfoTypeDTO);

    /**
     * Get all the accountInfoTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountInfoTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accountInfoType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountInfoTypeDTO> findOne(Long id);

    /**
     * Delete the "id" accountInfoType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

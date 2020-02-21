package mingorance.cano.personal.accounting.service;

import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mingorance.cano.personal.accounting.domain.EventInfoType}.
 */
public interface EventInfoTypeService {

    /**
     * Save a eventInfoType.
     *
     * @param eventInfoTypeDTO the entity to save.
     * @return the persisted entity.
     */
    EventInfoTypeDTO save(EventInfoTypeDTO eventInfoTypeDTO);

    /**
     * Get all the eventInfoTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventInfoTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventInfoType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventInfoTypeDTO> findOne(Long id);

    /**
     * Delete the "id" eventInfoType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

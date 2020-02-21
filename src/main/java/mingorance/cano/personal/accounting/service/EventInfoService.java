package mingorance.cano.personal.accounting.service;

import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mingorance.cano.personal.accounting.domain.EventInfo}.
 */
public interface EventInfoService {

    /**
     * Save a eventInfo.
     *
     * @param eventInfoDTO the entity to save.
     * @return the persisted entity.
     */
    EventInfoDTO save(EventInfoDTO eventInfoDTO);

    /**
     * Get all the eventInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventInfoDTO> findOne(Long id);

    /**
     * Delete the "id" eventInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

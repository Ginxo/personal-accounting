package mingorance.cano.personal.accounting.service;

import mingorance.cano.personal.accounting.service.dto.CalendarDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link mingorance.cano.personal.accounting.domain.Calendar}.
 */
public interface CalendarService {

    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save.
     * @return the persisted entity.
     */
    CalendarDTO save(CalendarDTO calendarDTO);

    /**
     * Get all the calendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendarDTO> findAll(Pageable pageable);

    /**
     * Get the "id" calendar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarDTO> findOne(Long id);

    /**
     * Delete the "id" calendar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

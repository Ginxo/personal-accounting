package mingorance.cano.personal.accounting.service.impl;

import mingorance.cano.personal.accounting.service.CalendarService;
import mingorance.cano.personal.accounting.domain.Calendar;
import mingorance.cano.personal.accounting.repository.CalendarRepository;
import mingorance.cano.personal.accounting.service.dto.CalendarDTO;
import mingorance.cano.personal.accounting.service.mapper.CalendarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Calendar}.
 */
@Service
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;

    private final CalendarMapper calendarMapper;

    public CalendarServiceImpl(CalendarRepository calendarRepository, CalendarMapper calendarMapper) {
        this.calendarRepository = calendarRepository;
        this.calendarMapper = calendarMapper;
    }

    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CalendarDTO save(CalendarDTO calendarDTO) {
        log.debug("Request to save Calendar : {}", calendarDTO);
        Calendar calendar = calendarMapper.toEntity(calendarDTO);
        calendar = calendarRepository.save(calendar);
        return calendarMapper.toDto(calendar);
    }

    /**
     * Get all the calendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CalendarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Calendars");
        return calendarRepository.findAll(pageable)
            .map(calendarMapper::toDto);
    }

    /**
     * Get one calendar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CalendarDTO> findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        return calendarRepository.findById(id)
            .map(calendarMapper::toDto);
    }

    /**
     * Delete the calendar by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.deleteById(id);
    }
}

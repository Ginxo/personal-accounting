package mingorance.cano.personal.accounting.service.impl;

import mingorance.cano.personal.accounting.service.EventInfoTypeService;
import mingorance.cano.personal.accounting.domain.EventInfoType;
import mingorance.cano.personal.accounting.repository.EventInfoTypeRepository;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventInfoType}.
 */
@Service
@Transactional
public class EventInfoTypeServiceImpl implements EventInfoTypeService {

    private final Logger log = LoggerFactory.getLogger(EventInfoTypeServiceImpl.class);

    private final EventInfoTypeRepository eventInfoTypeRepository;

    private final EventInfoTypeMapper eventInfoTypeMapper;

    public EventInfoTypeServiceImpl(EventInfoTypeRepository eventInfoTypeRepository, EventInfoTypeMapper eventInfoTypeMapper) {
        this.eventInfoTypeRepository = eventInfoTypeRepository;
        this.eventInfoTypeMapper = eventInfoTypeMapper;
    }

    /**
     * Save a eventInfoType.
     *
     * @param eventInfoTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventInfoTypeDTO save(EventInfoTypeDTO eventInfoTypeDTO) {
        log.debug("Request to save EventInfoType : {}", eventInfoTypeDTO);
        EventInfoType eventInfoType = eventInfoTypeMapper.toEntity(eventInfoTypeDTO);
        eventInfoType = eventInfoTypeRepository.save(eventInfoType);
        return eventInfoTypeMapper.toDto(eventInfoType);
    }

    /**
     * Get all the eventInfoTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventInfoTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventInfoTypes");
        return eventInfoTypeRepository.findAll(pageable)
            .map(eventInfoTypeMapper::toDto);
    }

    /**
     * Get one eventInfoType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventInfoTypeDTO> findOne(Long id) {
        log.debug("Request to get EventInfoType : {}", id);
        return eventInfoTypeRepository.findById(id)
            .map(eventInfoTypeMapper::toDto);
    }

    /**
     * Delete the eventInfoType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventInfoType : {}", id);
        eventInfoTypeRepository.deleteById(id);
    }
}

package mingorance.cano.personal.accounting.service.impl;

import mingorance.cano.personal.accounting.service.EventInfoService;
import mingorance.cano.personal.accounting.domain.EventInfo;
import mingorance.cano.personal.accounting.repository.EventInfoRepository;
import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventInfo}.
 */
@Service
@Transactional
public class EventInfoServiceImpl implements EventInfoService {

    private final Logger log = LoggerFactory.getLogger(EventInfoServiceImpl.class);

    private final EventInfoRepository eventInfoRepository;

    private final EventInfoMapper eventInfoMapper;

    public EventInfoServiceImpl(EventInfoRepository eventInfoRepository, EventInfoMapper eventInfoMapper) {
        this.eventInfoRepository = eventInfoRepository;
        this.eventInfoMapper = eventInfoMapper;
    }

    /**
     * Save a eventInfo.
     *
     * @param eventInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventInfoDTO save(EventInfoDTO eventInfoDTO) {
        log.debug("Request to save EventInfo : {}", eventInfoDTO);
        EventInfo eventInfo = eventInfoMapper.toEntity(eventInfoDTO);
        eventInfo = eventInfoRepository.save(eventInfo);
        return eventInfoMapper.toDto(eventInfo);
    }

    /**
     * Get all the eventInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventInfos");
        return eventInfoRepository.findAll(pageable)
            .map(eventInfoMapper::toDto);
    }

    /**
     * Get one eventInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventInfoDTO> findOne(Long id) {
        log.debug("Request to get EventInfo : {}", id);
        return eventInfoRepository.findById(id)
            .map(eventInfoMapper::toDto);
    }

    /**
     * Delete the eventInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventInfo : {}", id);
        eventInfoRepository.deleteById(id);
    }
}

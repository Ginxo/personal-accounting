package mingorance.cano.personal.accounting.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import mingorance.cano.personal.accounting.domain.EventInfo;
import mingorance.cano.personal.accounting.domain.*; // for static metamodels
import mingorance.cano.personal.accounting.repository.EventInfoRepository;
import mingorance.cano.personal.accounting.service.dto.EventInfoCriteria;
import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoMapper;

/**
 * Service for executing complex queries for {@link EventInfo} entities in the database.
 * The main input is a {@link EventInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventInfoDTO} or a {@link Page} of {@link EventInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventInfoQueryService extends QueryService<EventInfo> {

    private final Logger log = LoggerFactory.getLogger(EventInfoQueryService.class);

    private final EventInfoRepository eventInfoRepository;

    private final EventInfoMapper eventInfoMapper;

    public EventInfoQueryService(EventInfoRepository eventInfoRepository, EventInfoMapper eventInfoMapper) {
        this.eventInfoRepository = eventInfoRepository;
        this.eventInfoMapper = eventInfoMapper;
    }

    /**
     * Return a {@link List} of {@link EventInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventInfoDTO> findByCriteria(EventInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventInfo> specification = createSpecification(criteria);
        return eventInfoMapper.toDto(eventInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventInfoDTO> findByCriteria(EventInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventInfo> specification = createSpecification(criteria);
        return eventInfoRepository.findAll(specification, page)
            .map(eventInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventInfo> specification = createSpecification(criteria);
        return eventInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EventInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventInfo> createSpecification(EventInfoCriteria criteria) {
        Specification<EventInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EventInfo_.name));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EventInfo_.date));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), EventInfo_.amount));
            }
            if (criteria.getAmountType() != null) {
                specification = specification.and(buildSpecification(criteria.getAmountType(), EventInfo_.amountType));
            }
            if (criteria.getColour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColour(), EventInfo_.colour));
            }
            if (criteria.getCalendarId() != null) {
                specification = specification.and(buildSpecification(criteria.getCalendarId(),
                    root -> root.join(EventInfo_.calendar, JoinType.LEFT).get(Calendar_.id)));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(EventInfo_.type, JoinType.LEFT).get(EventInfoType_.id)));
            }
        }
        return specification;
    }
}

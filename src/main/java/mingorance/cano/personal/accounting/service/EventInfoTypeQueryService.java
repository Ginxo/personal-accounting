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

import mingorance.cano.personal.accounting.domain.EventInfoType;
import mingorance.cano.personal.accounting.domain.*; // for static metamodels
import mingorance.cano.personal.accounting.repository.EventInfoTypeRepository;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.EventInfoTypeMapper;

/**
 * Service for executing complex queries for {@link EventInfoType} entities in the database.
 * The main input is a {@link EventInfoTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventInfoTypeDTO} or a {@link Page} of {@link EventInfoTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventInfoTypeQueryService extends QueryService<EventInfoType> {

    private final Logger log = LoggerFactory.getLogger(EventInfoTypeQueryService.class);

    private final EventInfoTypeRepository eventInfoTypeRepository;

    private final EventInfoTypeMapper eventInfoTypeMapper;

    public EventInfoTypeQueryService(EventInfoTypeRepository eventInfoTypeRepository, EventInfoTypeMapper eventInfoTypeMapper) {
        this.eventInfoTypeRepository = eventInfoTypeRepository;
        this.eventInfoTypeMapper = eventInfoTypeMapper;
    }

    /**
     * Return a {@link List} of {@link EventInfoTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventInfoTypeDTO> findByCriteria(EventInfoTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventInfoType> specification = createSpecification(criteria);
        return eventInfoTypeMapper.toDto(eventInfoTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventInfoTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventInfoTypeDTO> findByCriteria(EventInfoTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventInfoType> specification = createSpecification(criteria);
        return eventInfoTypeRepository.findAll(specification, page)
            .map(eventInfoTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventInfoTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventInfoType> specification = createSpecification(criteria);
        return eventInfoTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link EventInfoTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventInfoType> createSpecification(EventInfoTypeCriteria criteria) {
        Specification<EventInfoType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventInfoType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EventInfoType_.name));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), EventInfoType_.icon));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(EventInfoType_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}

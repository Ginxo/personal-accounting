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

import mingorance.cano.personal.accounting.domain.AccountInfoType;
import mingorance.cano.personal.accounting.domain.*; // for static metamodels
import mingorance.cano.personal.accounting.repository.AccountInfoTypeRepository;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeCriteria;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoTypeMapper;

/**
 * Service for executing complex queries for {@link AccountInfoType} entities in the database.
 * The main input is a {@link AccountInfoTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountInfoTypeDTO} or a {@link Page} of {@link AccountInfoTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountInfoTypeQueryService extends QueryService<AccountInfoType> {

    private final Logger log = LoggerFactory.getLogger(AccountInfoTypeQueryService.class);

    private final AccountInfoTypeRepository accountInfoTypeRepository;

    private final AccountInfoTypeMapper accountInfoTypeMapper;

    public AccountInfoTypeQueryService(AccountInfoTypeRepository accountInfoTypeRepository, AccountInfoTypeMapper accountInfoTypeMapper) {
        this.accountInfoTypeRepository = accountInfoTypeRepository;
        this.accountInfoTypeMapper = accountInfoTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AccountInfoTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountInfoTypeDTO> findByCriteria(AccountInfoTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccountInfoType> specification = createSpecification(criteria);
        return accountInfoTypeMapper.toDto(accountInfoTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountInfoTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountInfoTypeDTO> findByCriteria(AccountInfoTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccountInfoType> specification = createSpecification(criteria);
        return accountInfoTypeRepository.findAll(specification, page)
            .map(accountInfoTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountInfoTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccountInfoType> specification = createSpecification(criteria);
        return accountInfoTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountInfoTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccountInfoType> createSpecification(AccountInfoTypeCriteria criteria) {
        Specification<AccountInfoType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccountInfoType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AccountInfoType_.name));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), AccountInfoType_.userId));
            }
            if (criteria.getCron() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCron(), AccountInfoType_.cron));
            }
            if (criteria.getIsOneTime() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOneTime(), AccountInfoType_.isOneTime));
            }
        }
        return specification;
    }
}

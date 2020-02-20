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

import mingorance.cano.personal.accounting.domain.AccountInfo;
import mingorance.cano.personal.accounting.domain.*; // for static metamodels
import mingorance.cano.personal.accounting.repository.AccountInfoRepository;
import mingorance.cano.personal.accounting.service.dto.AccountInfoCriteria;
import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoMapper;

/**
 * Service for executing complex queries for {@link AccountInfo} entities in the database.
 * The main input is a {@link AccountInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountInfoDTO} or a {@link Page} of {@link AccountInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountInfoQueryService extends QueryService<AccountInfo> {

    private final Logger log = LoggerFactory.getLogger(AccountInfoQueryService.class);

    private final AccountInfoRepository accountInfoRepository;

    private final AccountInfoMapper accountInfoMapper;

    public AccountInfoQueryService(AccountInfoRepository accountInfoRepository, AccountInfoMapper accountInfoMapper) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountInfoMapper = accountInfoMapper;
    }

    /**
     * Return a {@link List} of {@link AccountInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountInfoDTO> findByCriteria(AccountInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccountInfo> specification = createSpecification(criteria);
        return accountInfoMapper.toDto(accountInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountInfoDTO> findByCriteria(AccountInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccountInfo> specification = createSpecification(criteria);
        return accountInfoRepository.findAll(specification, page)
            .map(accountInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccountInfo> specification = createSpecification(criteria);
        return accountInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccountInfo> createSpecification(AccountInfoCriteria criteria) {
        Specification<AccountInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccountInfo_.id));
            }
            if (criteria.getConcept() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConcept(), AccountInfo_.concept));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), AccountInfo_.userId));
            }
            if (criteria.getStartingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartingDate(), AccountInfo_.startingDate));
            }
            if (criteria.getEndingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndingDate(), AccountInfo_.endingDate));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(AccountInfo_.type, JoinType.LEFT).get(AccountInfoType_.id)));
            }
        }
        return specification;
    }
}

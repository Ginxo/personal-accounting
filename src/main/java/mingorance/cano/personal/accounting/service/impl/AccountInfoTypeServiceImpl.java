package mingorance.cano.personal.accounting.service.impl;

import mingorance.cano.personal.accounting.service.AccountInfoTypeService;
import mingorance.cano.personal.accounting.domain.AccountInfoType;
import mingorance.cano.personal.accounting.repository.AccountInfoTypeRepository;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountInfoType}.
 */
@Service
@Transactional
public class AccountInfoTypeServiceImpl implements AccountInfoTypeService {

    private final Logger log = LoggerFactory.getLogger(AccountInfoTypeServiceImpl.class);

    private final AccountInfoTypeRepository accountInfoTypeRepository;

    private final AccountInfoTypeMapper accountInfoTypeMapper;

    public AccountInfoTypeServiceImpl(AccountInfoTypeRepository accountInfoTypeRepository, AccountInfoTypeMapper accountInfoTypeMapper) {
        this.accountInfoTypeRepository = accountInfoTypeRepository;
        this.accountInfoTypeMapper = accountInfoTypeMapper;
    }

    /**
     * Save a accountInfoType.
     *
     * @param accountInfoTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountInfoTypeDTO save(AccountInfoTypeDTO accountInfoTypeDTO) {
        log.debug("Request to save AccountInfoType : {}", accountInfoTypeDTO);
        AccountInfoType accountInfoType = accountInfoTypeMapper.toEntity(accountInfoTypeDTO);
        accountInfoType = accountInfoTypeRepository.save(accountInfoType);
        return accountInfoTypeMapper.toDto(accountInfoType);
    }

    /**
     * Get all the accountInfoTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountInfoTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountInfoTypes");
        return accountInfoTypeRepository.findAll(pageable)
            .map(accountInfoTypeMapper::toDto);
    }

    /**
     * Get one accountInfoType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountInfoTypeDTO> findOne(Long id) {
        log.debug("Request to get AccountInfoType : {}", id);
        return accountInfoTypeRepository.findById(id)
            .map(accountInfoTypeMapper::toDto);
    }

    /**
     * Delete the accountInfoType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountInfoType : {}", id);
        accountInfoTypeRepository.deleteById(id);
    }
}

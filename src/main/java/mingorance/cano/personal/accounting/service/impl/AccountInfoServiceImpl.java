package mingorance.cano.personal.accounting.service.impl;

import mingorance.cano.personal.accounting.service.AccountInfoService;
import mingorance.cano.personal.accounting.domain.AccountInfo;
import mingorance.cano.personal.accounting.repository.AccountInfoRepository;
import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;
import mingorance.cano.personal.accounting.service.mapper.AccountInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountInfo}.
 */
@Service
@Transactional
public class AccountInfoServiceImpl implements AccountInfoService {

    private final Logger log = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

    private final AccountInfoRepository accountInfoRepository;

    private final AccountInfoMapper accountInfoMapper;

    public AccountInfoServiceImpl(AccountInfoRepository accountInfoRepository, AccountInfoMapper accountInfoMapper) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountInfoMapper = accountInfoMapper;
    }

    /**
     * Save a accountInfo.
     *
     * @param accountInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountInfoDTO save(AccountInfoDTO accountInfoDTO) {
        log.debug("Request to save AccountInfo : {}", accountInfoDTO);
        AccountInfo accountInfo = accountInfoMapper.toEntity(accountInfoDTO);
        accountInfo = accountInfoRepository.save(accountInfo);
        return accountInfoMapper.toDto(accountInfo);
    }

    /**
     * Get all the accountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountInfos");
        return accountInfoRepository.findAll(pageable)
            .map(accountInfoMapper::toDto);
    }

    /**
     * Get one accountInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountInfoDTO> findOne(Long id) {
        log.debug("Request to get AccountInfo : {}", id);
        return accountInfoRepository.findById(id)
            .map(accountInfoMapper::toDto);
    }

    /**
     * Delete the accountInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountInfo : {}", id);
        accountInfoRepository.deleteById(id);
    }
}

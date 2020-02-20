package mingorance.cano.personal.accounting.service.mapper;


import mingorance.cano.personal.accounting.domain.*;
import mingorance.cano.personal.accounting.service.dto.AccountInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountInfo} and its DTO {@link AccountInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountInfoTypeMapper.class})
public interface AccountInfoMapper extends EntityMapper<AccountInfoDTO, AccountInfo> {

    @Mapping(source = "type.id", target = "typeId")
    AccountInfoDTO toDto(AccountInfo accountInfo);

    @Mapping(source = "typeId", target = "type")
    AccountInfo toEntity(AccountInfoDTO accountInfoDTO);

    default AccountInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(id);
        return accountInfo;
    }
}

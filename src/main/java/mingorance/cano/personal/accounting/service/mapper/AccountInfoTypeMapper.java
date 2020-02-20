package mingorance.cano.personal.accounting.service.mapper;


import mingorance.cano.personal.accounting.domain.*;
import mingorance.cano.personal.accounting.service.dto.AccountInfoTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountInfoType} and its DTO {@link AccountInfoTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountInfoTypeMapper extends EntityMapper<AccountInfoTypeDTO, AccountInfoType> {



    default AccountInfoType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountInfoType accountInfoType = new AccountInfoType();
        accountInfoType.setId(id);
        return accountInfoType;
    }
}

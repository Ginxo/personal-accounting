package mingorance.cano.personal.accounting.service.mapper;


import mingorance.cano.personal.accounting.domain.*;
import mingorance.cano.personal.accounting.service.dto.EventInfoTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventInfoType} and its DTO {@link EventInfoTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EventInfoTypeMapper extends EntityMapper<EventInfoTypeDTO, EventInfoType> {

    @Mapping(source = "user.id", target = "userId")
    EventInfoTypeDTO toDto(EventInfoType eventInfoType);

    @Mapping(source = "userId", target = "user")
    EventInfoType toEntity(EventInfoTypeDTO eventInfoTypeDTO);

    default EventInfoType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventInfoType eventInfoType = new EventInfoType();
        eventInfoType.setId(id);
        return eventInfoType;
    }
}

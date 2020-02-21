package mingorance.cano.personal.accounting.service.mapper;


import mingorance.cano.personal.accounting.domain.*;
import mingorance.cano.personal.accounting.service.dto.EventInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventInfo} and its DTO {@link EventInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CalendarMapper.class, EventInfoTypeMapper.class})
public interface EventInfoMapper extends EntityMapper<EventInfoDTO, EventInfo> {

    @Mapping(source = "calendar.id", target = "calendarId")
    @Mapping(source = "type.id", target = "typeId")
    EventInfoDTO toDto(EventInfo eventInfo);

    @Mapping(source = "calendarId", target = "calendar")
    @Mapping(source = "typeId", target = "type")
    EventInfo toEntity(EventInfoDTO eventInfoDTO);

    default EventInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventInfo eventInfo = new EventInfo();
        eventInfo.setId(id);
        return eventInfo;
    }
}

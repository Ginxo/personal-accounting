package mingorance.cano.personal.accounting.service.mapper;


import mingorance.cano.personal.accounting.domain.*;
import mingorance.cano.personal.accounting.service.dto.CalendarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Calendar} and its DTO {@link CalendarDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CalendarMapper extends EntityMapper<CalendarDTO, Calendar> {

    @Mapping(source = "user.id", target = "userId")
    CalendarDTO toDto(Calendar calendar);

    @Mapping(source = "userId", target = "user")
    Calendar toEntity(CalendarDTO calendarDTO);

    default Calendar fromId(Long id) {
        if (id == null) {
            return null;
        }
        Calendar calendar = new Calendar();
        calendar.setId(id);
        return calendar;
    }
}

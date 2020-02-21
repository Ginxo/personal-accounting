package mingorance.cano.personal.accounting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventInfoTypeMapperTest {

    private EventInfoTypeMapper eventInfoTypeMapper;

    @BeforeEach
    public void setUp() {
        eventInfoTypeMapper = new EventInfoTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventInfoTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventInfoTypeMapper.fromId(null)).isNull();
    }
}

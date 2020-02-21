package mingorance.cano.personal.accounting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventInfoMapperTest {

    private EventInfoMapper eventInfoMapper;

    @BeforeEach
    public void setUp() {
        eventInfoMapper = new EventInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventInfoMapper.fromId(null)).isNull();
    }
}

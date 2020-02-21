package mingorance.cano.personal.accounting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class EventInfoTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInfoTypeDTO.class);
        EventInfoTypeDTO eventInfoTypeDTO1 = new EventInfoTypeDTO();
        eventInfoTypeDTO1.setId(1L);
        EventInfoTypeDTO eventInfoTypeDTO2 = new EventInfoTypeDTO();
        assertThat(eventInfoTypeDTO1).isNotEqualTo(eventInfoTypeDTO2);
        eventInfoTypeDTO2.setId(eventInfoTypeDTO1.getId());
        assertThat(eventInfoTypeDTO1).isEqualTo(eventInfoTypeDTO2);
        eventInfoTypeDTO2.setId(2L);
        assertThat(eventInfoTypeDTO1).isNotEqualTo(eventInfoTypeDTO2);
        eventInfoTypeDTO1.setId(null);
        assertThat(eventInfoTypeDTO1).isNotEqualTo(eventInfoTypeDTO2);
    }
}

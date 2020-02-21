package mingorance.cano.personal.accounting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class EventInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInfoDTO.class);
        EventInfoDTO eventInfoDTO1 = new EventInfoDTO();
        eventInfoDTO1.setId(1L);
        EventInfoDTO eventInfoDTO2 = new EventInfoDTO();
        assertThat(eventInfoDTO1).isNotEqualTo(eventInfoDTO2);
        eventInfoDTO2.setId(eventInfoDTO1.getId());
        assertThat(eventInfoDTO1).isEqualTo(eventInfoDTO2);
        eventInfoDTO2.setId(2L);
        assertThat(eventInfoDTO1).isNotEqualTo(eventInfoDTO2);
        eventInfoDTO1.setId(null);
        assertThat(eventInfoDTO1).isNotEqualTo(eventInfoDTO2);
    }
}

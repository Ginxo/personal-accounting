package mingorance.cano.personal.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class EventInfoTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInfoType.class);
        EventInfoType eventInfoType1 = new EventInfoType();
        eventInfoType1.setId(1L);
        EventInfoType eventInfoType2 = new EventInfoType();
        eventInfoType2.setId(eventInfoType1.getId());
        assertThat(eventInfoType1).isEqualTo(eventInfoType2);
        eventInfoType2.setId(2L);
        assertThat(eventInfoType1).isNotEqualTo(eventInfoType2);
        eventInfoType1.setId(null);
        assertThat(eventInfoType1).isNotEqualTo(eventInfoType2);
    }
}

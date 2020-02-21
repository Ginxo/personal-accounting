package mingorance.cano.personal.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class EventInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInfo.class);
        EventInfo eventInfo1 = new EventInfo();
        eventInfo1.setId(1L);
        EventInfo eventInfo2 = new EventInfo();
        eventInfo2.setId(eventInfo1.getId());
        assertThat(eventInfo1).isEqualTo(eventInfo2);
        eventInfo2.setId(2L);
        assertThat(eventInfo1).isNotEqualTo(eventInfo2);
        eventInfo1.setId(null);
        assertThat(eventInfo1).isNotEqualTo(eventInfo2);
    }
}

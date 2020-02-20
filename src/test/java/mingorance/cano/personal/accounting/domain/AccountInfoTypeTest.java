package mingorance.cano.personal.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class AccountInfoTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountInfoType.class);
        AccountInfoType accountInfoType1 = new AccountInfoType();
        accountInfoType1.setId(1L);
        AccountInfoType accountInfoType2 = new AccountInfoType();
        accountInfoType2.setId(accountInfoType1.getId());
        assertThat(accountInfoType1).isEqualTo(accountInfoType2);
        accountInfoType2.setId(2L);
        assertThat(accountInfoType1).isNotEqualTo(accountInfoType2);
        accountInfoType1.setId(null);
        assertThat(accountInfoType1).isNotEqualTo(accountInfoType2);
    }
}

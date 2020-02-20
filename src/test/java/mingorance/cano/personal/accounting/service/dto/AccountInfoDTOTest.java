package mingorance.cano.personal.accounting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class AccountInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountInfoDTO.class);
        AccountInfoDTO accountInfoDTO1 = new AccountInfoDTO();
        accountInfoDTO1.setId(1L);
        AccountInfoDTO accountInfoDTO2 = new AccountInfoDTO();
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
        accountInfoDTO2.setId(accountInfoDTO1.getId());
        assertThat(accountInfoDTO1).isEqualTo(accountInfoDTO2);
        accountInfoDTO2.setId(2L);
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
        accountInfoDTO1.setId(null);
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
    }
}

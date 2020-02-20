package mingorance.cano.personal.accounting.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mingorance.cano.personal.accounting.web.rest.TestUtil;

public class AccountInfoTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountInfoTypeDTO.class);
        AccountInfoTypeDTO accountInfoTypeDTO1 = new AccountInfoTypeDTO();
        accountInfoTypeDTO1.setId(1L);
        AccountInfoTypeDTO accountInfoTypeDTO2 = new AccountInfoTypeDTO();
        assertThat(accountInfoTypeDTO1).isNotEqualTo(accountInfoTypeDTO2);
        accountInfoTypeDTO2.setId(accountInfoTypeDTO1.getId());
        assertThat(accountInfoTypeDTO1).isEqualTo(accountInfoTypeDTO2);
        accountInfoTypeDTO2.setId(2L);
        assertThat(accountInfoTypeDTO1).isNotEqualTo(accountInfoTypeDTO2);
        accountInfoTypeDTO1.setId(null);
        assertThat(accountInfoTypeDTO1).isNotEqualTo(accountInfoTypeDTO2);
    }
}

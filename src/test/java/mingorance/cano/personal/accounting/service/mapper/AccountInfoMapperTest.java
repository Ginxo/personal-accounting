package mingorance.cano.personal.accounting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountInfoMapperTest {

    private AccountInfoMapper accountInfoMapper;

    @BeforeEach
    public void setUp() {
        accountInfoMapper = new AccountInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accountInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountInfoMapper.fromId(null)).isNull();
    }
}

package mingorance.cano.personal.accounting.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountInfoTypeMapperTest {

    private AccountInfoTypeMapper accountInfoTypeMapper;

    @BeforeEach
    public void setUp() {
        accountInfoTypeMapper = new AccountInfoTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accountInfoTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountInfoTypeMapper.fromId(null)).isNull();
    }
}

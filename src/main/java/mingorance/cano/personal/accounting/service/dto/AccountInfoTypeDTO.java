package mingorance.cano.personal.accounting.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.AccountInfoType} entity.
 */
public class AccountInfoTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long userId;

    private String cron;

    private Boolean isOneTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean isIsOneTime() {
        return isOneTime;
    }

    public void setIsOneTime(Boolean isOneTime) {
        this.isOneTime = isOneTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountInfoTypeDTO accountInfoTypeDTO = (AccountInfoTypeDTO) o;
        if (accountInfoTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountInfoTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountInfoTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId=" + getUserId() +
            ", cron='" + getCron() + "'" +
            ", isOneTime='" + isIsOneTime() + "'" +
            "}";
    }
}

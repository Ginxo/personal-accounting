package mingorance.cano.personal.accounting.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.EventInfoType} entity.
 */
public class EventInfoTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String icon;


    private Long userId;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventInfoTypeDTO eventInfoTypeDTO = (EventInfoTypeDTO) o;
        if (eventInfoTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventInfoTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventInfoTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", icon='" + getIcon() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}

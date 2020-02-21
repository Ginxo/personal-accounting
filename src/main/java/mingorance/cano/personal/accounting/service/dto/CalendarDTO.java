package mingorance.cano.personal.accounting.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.Calendar} entity.
 */
public class CalendarDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String colour;

    private String description;

    @NotNull
    private String timeZone;

    @NotNull
    private Boolean enabled;


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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

        CalendarDTO calendarDTO = (CalendarDTO) o;
        if (calendarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalendarDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", colour='" + getColour() + "'" +
            ", description='" + getDescription() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}

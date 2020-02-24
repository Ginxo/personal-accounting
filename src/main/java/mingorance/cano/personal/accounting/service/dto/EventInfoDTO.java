package mingorance.cano.personal.accounting.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import mingorance.cano.personal.accounting.domain.enumeration.AmountType;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.EventInfo} entity.
 */
public class EventInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private AmountType amountType;

    @Lob
    private String iterateInformation;

    @NotNull
    private String colour;


    private Long calendarId;

    private Long typeId;

    private EventInfoTypeDTO type;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public String getIterateInformation() {
        return iterateInformation;
    }

    public void setIterateInformation(String iterateInformation) {
        this.iterateInformation = iterateInformation;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long eventInfoTypeId) {
        this.typeId = eventInfoTypeId;
    }

    public EventInfoTypeDTO getType() {
        return type;
    }

    public void setType(EventInfoTypeDTO type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventInfoDTO eventInfoDTO = (EventInfoDTO) o;
        if (eventInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventInfoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", amountType='" + getAmountType() + "'" +
            ", iterateInformation='" + getIterateInformation() + "'" +
            ", colour='" + getColour() + "'" +
            ", calendarId=" + getCalendarId() +
            ", typeId=" + getTypeId() +
            "}";
    }
}

package mingorance.cano.personal.accounting.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import mingorance.cano.personal.accounting.domain.enumeration.AmountType;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.EventInfo} entity.
 */
public class EventInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", amount=" + getAmount() +
            ", amountType='" + getAmountType() + "'" +
            ", iterateInformation='" + getIterateInformation() + "'" +
            ", colour='" + getColour() + "'" +
            ", calendarId=" + getCalendarId() +
            ", typeId=" + getTypeId() +
            "}";
    }
}

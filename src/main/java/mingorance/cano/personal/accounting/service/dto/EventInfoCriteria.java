package mingorance.cano.personal.accounting.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import mingorance.cano.personal.accounting.domain.enumeration.AmountType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link mingorance.cano.personal.accounting.domain.EventInfo} entity. This class is used
 * in {@link mingorance.cano.personal.accounting.web.rest.EventInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventInfoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AmountType
     */
    public static class AmountTypeFilter extends Filter<AmountType> {

        public AmountTypeFilter() {
        }

        public AmountTypeFilter(AmountTypeFilter filter) {
            super(filter);
        }

        @Override
        public AmountTypeFilter copy() {
            return new AmountTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter date;

    private BigDecimalFilter amount;

    private AmountTypeFilter amountType;

    private StringFilter colour;

    private LongFilter calendarId;

    private LongFilter typeId;

    public EventInfoCriteria() {
    }

    public EventInfoCriteria(EventInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.amountType = other.amountType == null ? null : other.amountType.copy();
        this.colour = other.colour == null ? null : other.colour.copy();
        this.calendarId = other.calendarId == null ? null : other.calendarId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
    }

    @Override
    public EventInfoCriteria copy() {
        return new EventInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public AmountTypeFilter getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountTypeFilter amountType) {
        this.amountType = amountType;
    }

    public StringFilter getColour() {
        return colour;
    }

    public void setColour(StringFilter colour) {
        this.colour = colour;
    }

    public LongFilter getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(LongFilter calendarId) {
        this.calendarId = calendarId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventInfoCriteria that = (EventInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(date, that.date) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(amountType, that.amountType) &&
            Objects.equals(colour, that.colour) &&
            Objects.equals(calendarId, that.calendarId) &&
            Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        date,
        amount,
        amountType,
        colour,
        calendarId,
        typeId
        );
    }

    @Override
    public String toString() {
        return "EventInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (amountType != null ? "amountType=" + amountType + ", " : "") +
                (colour != null ? "colour=" + colour + ", " : "") +
                (calendarId != null ? "calendarId=" + calendarId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
            "}";
    }

}

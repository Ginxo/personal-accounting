package mingorance.cano.personal.accounting.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link mingorance.cano.personal.accounting.domain.Calendar} entity. This class is used
 * in {@link mingorance.cano.personal.accounting.web.rest.CalendarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /calendars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CalendarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter colour;

    private StringFilter description;

    private StringFilter timeZone;

    private BooleanFilter enabled;

    private LongFilter userId;

    public CalendarCriteria() {
    }

    public CalendarCriteria(CalendarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.colour = other.colour == null ? null : other.colour.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.timeZone = other.timeZone == null ? null : other.timeZone.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public CalendarCriteria copy() {
        return new CalendarCriteria(this);
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

    public StringFilter getColour() {
        return colour;
    }

    public void setColour(StringFilter colour) {
        this.colour = colour;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(StringFilter timeZone) {
        this.timeZone = timeZone;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
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
        final CalendarCriteria that = (CalendarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(colour, that.colour) &&
            Objects.equals(description, that.description) &&
            Objects.equals(timeZone, that.timeZone) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        colour,
        description,
        timeZone,
        enabled,
        userId
        );
    }

    @Override
    public String toString() {
        return "CalendarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (colour != null ? "colour=" + colour + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (timeZone != null ? "timeZone=" + timeZone + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}

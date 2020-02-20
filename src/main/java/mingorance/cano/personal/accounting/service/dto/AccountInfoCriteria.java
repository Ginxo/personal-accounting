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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link mingorance.cano.personal.accounting.domain.AccountInfo} entity. This class is used
 * in {@link mingorance.cano.personal.accounting.web.rest.AccountInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /account-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter concept;

    private LongFilter userId;

    private LocalDateFilter startingDate;

    private LocalDateFilter endingDate;

    private LongFilter typeId;

    public AccountInfoCriteria() {
    }

    public AccountInfoCriteria(AccountInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.concept = other.concept == null ? null : other.concept.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.startingDate = other.startingDate == null ? null : other.startingDate.copy();
        this.endingDate = other.endingDate == null ? null : other.endingDate.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
    }

    @Override
    public AccountInfoCriteria copy() {
        return new AccountInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConcept() {
        return concept;
    }

    public void setConcept(StringFilter concept) {
        this.concept = concept;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LocalDateFilter getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateFilter startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateFilter getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateFilter endingDate) {
        this.endingDate = endingDate;
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
        final AccountInfoCriteria that = (AccountInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(concept, that.concept) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(startingDate, that.startingDate) &&
            Objects.equals(endingDate, that.endingDate) &&
            Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        concept,
        userId,
        startingDate,
        endingDate,
        typeId
        );
    }

    @Override
    public String toString() {
        return "AccountInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (concept != null ? "concept=" + concept + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (startingDate != null ? "startingDate=" + startingDate + ", " : "") +
                (endingDate != null ? "endingDate=" + endingDate + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
            "}";
    }

}

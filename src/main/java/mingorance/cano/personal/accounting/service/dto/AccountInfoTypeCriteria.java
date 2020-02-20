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
 * Criteria class for the {@link mingorance.cano.personal.accounting.domain.AccountInfoType} entity. This class is used
 * in {@link mingorance.cano.personal.accounting.web.rest.AccountInfoTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /account-info-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountInfoTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter userId;

    private StringFilter cron;

    private BooleanFilter isOneTime;

    public AccountInfoTypeCriteria() {
    }

    public AccountInfoTypeCriteria(AccountInfoTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.cron = other.cron == null ? null : other.cron.copy();
        this.isOneTime = other.isOneTime == null ? null : other.isOneTime.copy();
    }

    @Override
    public AccountInfoTypeCriteria copy() {
        return new AccountInfoTypeCriteria(this);
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

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getCron() {
        return cron;
    }

    public void setCron(StringFilter cron) {
        this.cron = cron;
    }

    public BooleanFilter getIsOneTime() {
        return isOneTime;
    }

    public void setIsOneTime(BooleanFilter isOneTime) {
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
        final AccountInfoTypeCriteria that = (AccountInfoTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(cron, that.cron) &&
            Objects.equals(isOneTime, that.isOneTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        userId,
        cron,
        isOneTime
        );
    }

    @Override
    public String toString() {
        return "AccountInfoTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (cron != null ? "cron=" + cron + ", " : "") +
                (isOneTime != null ? "isOneTime=" + isOneTime + ", " : "") +
            "}";
    }

}

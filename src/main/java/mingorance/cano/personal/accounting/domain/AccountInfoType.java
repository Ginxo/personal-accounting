package mingorance.cano.personal.accounting.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AccountInfoType.
 */
@Entity
@Table(name = "account_info_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountInfoType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "cron")
    private String cron;

    @Column(name = "is_one_time")
    private Boolean isOneTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AccountInfoType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public AccountInfoType userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCron() {
        return cron;
    }

    public AccountInfoType cron(String cron) {
        this.cron = cron;
        return this;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean isIsOneTime() {
        return isOneTime;
    }

    public AccountInfoType isOneTime(Boolean isOneTime) {
        this.isOneTime = isOneTime;
        return this;
    }

    public void setIsOneTime(Boolean isOneTime) {
        this.isOneTime = isOneTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountInfoType)) {
            return false;
        }
        return id != null && id.equals(((AccountInfoType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountInfoType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId=" + getUserId() +
            ", cron='" + getCron() + "'" +
            ", isOneTime='" + isIsOneTime() + "'" +
            "}";
    }
}

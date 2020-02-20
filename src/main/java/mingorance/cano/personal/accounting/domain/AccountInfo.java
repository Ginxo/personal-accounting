package mingorance.cano.personal.accounting.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AccountInfo.
 */
@Entity
@Table(name = "account_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "concept", nullable = false)
    private String concept;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("accountInfos")
    private AccountInfoType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public AccountInfo concept(String concept) {
        this.concept = concept;
        return this;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getUserId() {
        return userId;
    }

    public AccountInfo userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public AccountInfo startingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
        return this;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public AccountInfo endingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
        return this;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public AccountInfoType getType() {
        return type;
    }

    public AccountInfo type(AccountInfoType accountInfoType) {
        this.type = accountInfoType;
        return this;
    }

    public void setType(AccountInfoType accountInfoType) {
        this.type = accountInfoType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountInfo)) {
            return false;
        }
        return id != null && id.equals(((AccountInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", userId=" + getUserId() +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            "}";
    }
}

package mingorance.cano.personal.accounting.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import mingorance.cano.personal.accounting.domain.enumeration.AmountType;

/**
 * A EventInfo.
 */
@Entity
@Table(name = "event_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "amount_type", nullable = false)
    private AmountType amountType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "iterate_information")
    private String iterateInformation;

    @NotNull
    @Column(name = "colour", nullable = false)
    private String colour;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("eventInfos")
    private Calendar calendar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("eventInfos")
    private EventInfoType type;

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

    public EventInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public EventInfo date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public EventInfo amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public EventInfo amountType(AmountType amountType) {
        this.amountType = amountType;
        return this;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public String getIterateInformation() {
        return iterateInformation;
    }

    public EventInfo iterateInformation(String iterateInformation) {
        this.iterateInformation = iterateInformation;
        return this;
    }

    public void setIterateInformation(String iterateInformation) {
        this.iterateInformation = iterateInformation;
    }

    public String getColour() {
        return colour;
    }

    public EventInfo colour(String colour) {
        this.colour = colour;
        return this;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public EventInfo calendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public EventInfoType getType() {
        return type;
    }

    public EventInfo type(EventInfoType eventInfoType) {
        this.type = eventInfoType;
        return this;
    }

    public void setType(EventInfoType eventInfoType) {
        this.type = eventInfoType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventInfo)) {
            return false;
        }
        return id != null && id.equals(((EventInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", amountType='" + getAmountType() + "'" +
            ", iterateInformation='" + getIterateInformation() + "'" +
            ", colour='" + getColour() + "'" +
            "}";
    }
}

package mingorance.cano.personal.accounting.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mingorance.cano.personal.accounting.domain.AccountInfo} entity.
 */
public class AccountInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String concept;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDate startingDate;

    private LocalDate endingDate;


    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long accountInfoTypeId) {
        this.typeId = accountInfoTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) o;
        if (accountInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountInfoDTO{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", userId=" + getUserId() +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", typeId=" + getTypeId() +
            "}";
    }
}

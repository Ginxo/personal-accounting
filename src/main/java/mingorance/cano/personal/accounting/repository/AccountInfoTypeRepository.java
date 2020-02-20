package mingorance.cano.personal.accounting.repository;

import mingorance.cano.personal.accounting.domain.AccountInfoType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountInfoType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountInfoTypeRepository extends JpaRepository<AccountInfoType, Long>, JpaSpecificationExecutor<AccountInfoType> {

}

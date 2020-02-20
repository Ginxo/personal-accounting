package mingorance.cano.personal.accounting.repository;

import mingorance.cano.personal.accounting.domain.AccountInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long>, JpaSpecificationExecutor<AccountInfo> {

}

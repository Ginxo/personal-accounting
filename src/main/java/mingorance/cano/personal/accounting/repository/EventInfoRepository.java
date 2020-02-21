package mingorance.cano.personal.accounting.repository;

import mingorance.cano.personal.accounting.domain.EventInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, Long>, JpaSpecificationExecutor<EventInfo> {

}

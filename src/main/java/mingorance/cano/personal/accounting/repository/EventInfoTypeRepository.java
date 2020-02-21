package mingorance.cano.personal.accounting.repository;

import mingorance.cano.personal.accounting.domain.EventInfoType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EventInfoType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventInfoTypeRepository extends JpaRepository<EventInfoType, Long>, JpaSpecificationExecutor<EventInfoType> {

    @Query("select eventInfoType from EventInfoType eventInfoType where eventInfoType.user.login = ?#{principal.username}")
    List<EventInfoType> findByUserIsCurrentUser();

}

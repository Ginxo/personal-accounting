package mingorance.cano.personal.accounting.repository;

import mingorance.cano.personal.accounting.domain.Calendar;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>, JpaSpecificationExecutor<Calendar> {

    @Query("select calendar from Calendar calendar where calendar.user.login = ?#{principal.username}")
    List<Calendar> findByUserIsCurrentUser();

}

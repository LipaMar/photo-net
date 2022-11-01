package photonet.server.domain.meetings.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.meetings.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  Optional<Schedule> findByOwnerUserName(String userName);

}

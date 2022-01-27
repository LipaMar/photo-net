package photonet.server.domain.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.meetings.entity.Schedule;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByOwnerUserName(String userName);

}

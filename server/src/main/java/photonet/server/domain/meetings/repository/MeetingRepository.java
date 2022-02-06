package photonet.server.domain.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.meetings.entity.Meeting;
import photonet.server.domain.meetings.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> removeAllByScheduleAndDateAndStatus(Schedule schedule, LocalDate date, MeetingStatus status);

}

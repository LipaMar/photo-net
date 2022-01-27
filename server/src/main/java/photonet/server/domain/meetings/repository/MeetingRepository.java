package photonet.server.domain.meetings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photonet.server.domain.meetings.entity.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}

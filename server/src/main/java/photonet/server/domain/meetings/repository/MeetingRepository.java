package photonet.server.domain.meetings.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.entity.User;
import photonet.server.domain.meetings.entity.Meeting;
import photonet.server.domain.meetings.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> removeAllByScheduleAndDateAndStatus(Schedule schedule, LocalDate date, MeetingStatus status);

    List<Meeting> findAllByUserBooked(User user);

    @Modifying
    @Query("UPDATE Meeting m SET m.status = :newStatus WHERE m.id = :meetingId")
    void updateMeetingStatus(Long meetingId, MeetingStatus newStatus);

    void deleteAllByStatus(MeetingStatus status);

    Meeting getMeetingById(Long id);
}

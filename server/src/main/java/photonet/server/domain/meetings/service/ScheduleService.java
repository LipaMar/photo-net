package photonet.server.domain.meetings.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.domain.meetings.ScheduleMapper;
import photonet.server.domain.meetings.entity.Schedule;
import photonet.server.domain.meetings.repository.ScheduleRepository;
import photonet.server.webui.dto.ScheduleDto;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDto getScheduleForUser(String userName) {
        var schedule = scheduleRepository.findByOwnerUserName(userName)
                .orElse(mockSchedule());
        return scheduleMapper.scheduleToDto(schedule);
    }

    private Schedule mockSchedule() {
            var schedule = new Schedule();
            schedule.setDisabled(true);
            return schedule;
    }
}

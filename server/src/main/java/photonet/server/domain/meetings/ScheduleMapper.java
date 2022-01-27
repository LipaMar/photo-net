package photonet.server.domain.meetings;

import org.mapstruct.Mapper;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.meetings.entity.Meeting;
import photonet.server.domain.meetings.entity.Schedule;
import photonet.server.webui.dto.MeetingDto;
import photonet.server.webui.dto.ScheduleDto;

@Mapper(uses = UserMapper.class)
public interface ScheduleMapper {

    ScheduleDto scheduleToDto(Schedule schedule);

    MeetingDto meetingToDto(Meeting meeting);

}

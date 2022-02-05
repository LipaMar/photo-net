package photonet.server.domain.meetings;

import lombok.Setter;
import org.mapstruct.Mapper;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.meetings.entity.Meeting;
import photonet.server.domain.meetings.entity.Schedule;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.MeetingDto;
import photonet.server.webui.dto.ScheduleDto;

@Mapper(uses = UserMapper.class)
@Setter
public abstract class ScheduleMapper {

    private UserRepository userRepository;

    public abstract ScheduleDto scheduleToDto(Schedule schedule);

    public abstract MeetingDto meetingToDto(Meeting meeting);

    public abstract Schedule ScheduleDtoToEntity(ScheduleDto dto);

    public abstract Meeting MeetingDtoToEntity(MeetingDto dto);


    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                             .orElseThrow(NotFoundRestException::new);
    }

}

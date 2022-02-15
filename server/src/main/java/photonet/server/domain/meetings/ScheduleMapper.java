package photonet.server.domain.meetings;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    public abstract ScheduleDto scheduleToDto(Schedule schedule);

    @Mapping(target = "owner", source = "schedule.owner")
    public abstract MeetingDto meetingToDto(Meeting meeting);

    public abstract Schedule ScheduleDtoToEntity(ScheduleDto dto);

    public abstract Meeting MeetingDtoToEntity(MeetingDto dto);


    public User findUserByUserName(String userName) {
        if(userName == null ) {
            return null;
        }
        return userRepository.findByUserName(userName)
                             .orElseThrow(NotFoundRestException::new);
    }

}

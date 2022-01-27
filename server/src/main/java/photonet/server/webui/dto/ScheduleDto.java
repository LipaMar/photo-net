package photonet.server.webui.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleDto {

    private boolean isDisabled;
    private List<MeetingDto> meetings;
    private String owner;

}

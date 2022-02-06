package photonet.server.webui.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleDto {

    private boolean isDisabled;
    private List<MeetingDto> meetings;
    private String owner;
    private LocalDate saveDate;
}

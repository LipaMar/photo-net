package photonet.server.webui.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ScheduleDto {

  private boolean isDisabled;
  private List<MeetingDto> meetings;
  private String owner;
  private LocalDate saveDate;
}

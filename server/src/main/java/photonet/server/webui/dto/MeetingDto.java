package photonet.server.webui.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import photonet.server.core.enums.MeetingStatus;

@Data
public class MeetingDto {

  private Long id;
  private LocalDate date;
  private LocalTime timeStart;
  private BigDecimal price;
  private MeetingStatus status;
  private String userBooked;
  private String owner;
  private Integer rate;

}

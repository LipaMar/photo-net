package photonet.server.webui.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class BookMeetingDto {

  private Long id;
  private LocalDate date;
  private LocalTime timeStart;
  private BigDecimal price;
  private String photographer;

}

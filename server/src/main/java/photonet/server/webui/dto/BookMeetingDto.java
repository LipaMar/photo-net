package photonet.server.webui.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookMeetingDto {

    private Long id;
    private LocalDate date;
    private LocalTime timeStart;
    private BigDecimal price;
    private String photographer;

}

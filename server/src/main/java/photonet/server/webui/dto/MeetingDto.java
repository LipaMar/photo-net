package photonet.server.webui.dto;

import lombok.Data;
import photonet.server.core.enums.MeetingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MeetingDto {

    private Long id;
    private LocalDate date;
    private LocalTime timeStart;
    private BigDecimal price;
    private MeetingStatus status;
    private String userBooked;
    private String owner;

}

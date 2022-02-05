package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.meetings.service.ScheduleService;
import photonet.server.webui.dto.ScheduleDto;

@RestController
@RequestMapping(Endpoints.SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ScheduleDto getUserSchedule(@RequestParam String userName) {
        return scheduleService.getScheduleForUser(userName);
    }
    @PostMapping
    public void createFreeEvent(ScheduleDto scheduleDto){
        scheduleService.createFreeEvent(scheduleDto);
    }

}

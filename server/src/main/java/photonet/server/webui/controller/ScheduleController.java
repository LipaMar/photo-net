package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.meetings.service.ScheduleService;
import photonet.server.webui.dto.BookMeetingDto;
import photonet.server.webui.dto.MeetingDto;
import photonet.server.webui.dto.ScheduleDto;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(Endpoints.SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ScheduleDto getUserSchedule(@RequestParam String userName) {
        return scheduleService.getScheduleForUser(userName);
    }

    @GetMapping(Endpoints.MEETING)
    public MeetingDto getUserMeeting(@RequestParam Long id) {
        return scheduleService.getMeetingById(id);
    }

    @GetMapping(Endpoints.MEETING_BY_HOUR)
    public MeetingDto getUserMeetingByHour(@RequestParam String owner, @RequestParam String date, @RequestParam LocalTime hour) {
        return scheduleService.getMeetingByDateAndHour(owner, date, hour);
    }

    @GetMapping(Endpoints.MY_MEETINGS)
    public List<MeetingDto> getLoggedUSerMeetings() {
        return scheduleService.getLoggedUSerMeetings();
    }

    @PostMapping
    public void createFreeEvent(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.createFreeEvent(scheduleDto);
    }

    @PostMapping(Endpoints.BOOK)
    public void bookMeeting(@RequestBody BookMeetingDto dto) {
        scheduleService.bookMeeting(dto);
    }

}

package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.meetings.service.ScheduleService;
import photonet.server.domain.service.UserService;
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
        scheduleService.updateMeetings(userName);
        return scheduleService.getScheduleForUser(userName);
    }

    @GetMapping(Endpoints.MEETING + "/{meetingId}")
    public MeetingDto getUserMeeting(@PathVariable Long meetingId) {
        return scheduleService.getMeetingById(meetingId);
    }

    @PutMapping(Endpoints.MEETING + "/{meetingId}")
    public void updateMeetingStatus(@PathVariable Long meetingId, @RequestParam MeetingStatus status) {
        scheduleService.updateMeetingStatus(meetingId, status);
    }

    @GetMapping(Endpoints.MEETING_BY_HOUR)
    public MeetingDto getUserMeetingByHour(@RequestParam String owner, @RequestParam String date, @RequestParam LocalTime hour) {
        return scheduleService.getMeetingByDateAndHour(owner, date, hour);
    }

    @GetMapping(Endpoints.MY_MEETINGS)
    public List<MeetingDto> getLoggedUSerMeetings() {
        scheduleService.updateMeetings(SecurityUtils.loggedUserName());
        return scheduleService.getLoggedUserMeetings();
    }

    @PostMapping
    public void createFreeEvent(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.createFreeEvent(scheduleDto);
    }

    @PostMapping(Endpoints.BOOK)
    public void bookMeeting(@RequestBody BookMeetingDto dto) {
        scheduleService.bookMeeting(dto);
    }

    @PostMapping(Endpoints.MEETING + "/{meetingId}")
    public void rateMeeting(@PathVariable Long meetingId, @RequestBody int rate) {
        scheduleService.rateMeeting(meetingId, rate);
    }

}

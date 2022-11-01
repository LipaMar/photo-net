package photonet.server.webui.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.meetings.dto.ChatRoomDto;
import photonet.server.domain.meetings.dto.MessageDto;
import photonet.server.domain.messages.service.MessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.CHAT)
public class MessageController {

  private final MessageService messageService;

  @GetMapping
  public List<ChatRoomDto> getChatRooms(@RequestParam String userName) {
    return messageService.getChatRooms(userName);
  }

  @PostMapping
  public void startNeConversation(@RequestBody String userName) {
    messageService.startNewChat(userName);

  }

  @PostMapping(Endpoints.MESSAGE)
  public void newMessage(@RequestBody MessageDto message) {
    messageService.processMessage(message);
  }
}

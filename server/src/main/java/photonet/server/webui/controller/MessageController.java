package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.meetings.dto.ChatRoomDto;
import photonet.server.domain.meetings.dto.MessageDto;
import photonet.server.domain.messages.service.MessageService;

import java.util.List;

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
    public void startNeConversation(@RequestBody String userName){
        messageService.startNewChat(userName);

    }

    @PostMapping("/message")
    public void newMessage(@RequestBody MessageDto message) {
        messageService.processMessage(message);
    }
}

package photonet.server.domain.messages.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.User;
import photonet.server.domain.meetings.dto.ChatRoomDto;
import photonet.server.domain.meetings.dto.MessageDto;
import photonet.server.domain.meetings.mapper.ChatMapper;
import photonet.server.domain.messages.entity.ChatRoom;
import photonet.server.domain.messages.entity.Message;
import photonet.server.domain.messages.repository.ChatRoomRepository;
import photonet.server.domain.messages.repository.MessageRepository;
import photonet.server.domain.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatMapper chatMapper;


    public List<ChatRoomDto> getChatRooms(String userName) {
        return chatRoomRepository.findAllChatRoomsForUserId(userName)
                                 .stream()
                                 .map(chatMapper::mapChatRoomToDto)
                                 .filter(dto -> !dto.getMessages().isEmpty())
                                 .collect(Collectors.toList());
    }

    @Transactional
    public void processMessage(MessageDto messageDto) {
        final var chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId()).orElseThrow();
        final var loggedUserName = SecurityUtils.loggedUserName();
        final var loggedUser = userService.findByUserName(loggedUserName);
        if (!loggedUserName.equals(messageDto.getAuthor()) || !(loggedUser.equals(chatRoom.getRecipient()) || loggedUser.equals(chatRoom.getSender()))) {
            throw new ForbiddenRestException();
        }
        final var message = Message.builder()
                                   .author(loggedUser)
                                   .content(messageDto.getContent())
                                   .chatRoom(chatRoom)
                                   .timestamp(LocalDateTime.now())
                                   .build();
        messageRepository.save(message);
    }


    @Transactional
    public void startNewChat(String userName) {
        final var loggedUser = userService.getLoggedUser();
        final var recipient = userService.findByUserName(userName);
        final var chatRoom = chatRoomRepository.findBySenderAndRecipient(loggedUser, recipient)
                                               .orElse(buildChatRoom(loggedUser, recipient));
        chatRoomRepository.save(chatRoom);
    }

    private ChatRoom buildChatRoom(User loggedUser, User recipient) {
        return ChatRoom.builder().recipient(recipient).sender(loggedUser).build();
    }
}

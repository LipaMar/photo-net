package photonet.server.domain.meetings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.meetings.dto.ChatRoomDto;
import photonet.server.domain.meetings.dto.MessageDto;
import photonet.server.domain.messages.entity.ChatRoom;
import photonet.server.domain.messages.entity.Message;

@Mapper(uses = {UserMapper.class})
public interface ChatMapper {


  ChatRoomDto mapChatRoomToDto(ChatRoom chatRoom);

  @Mapping(target = "chatRoomId", source = "chatRoom.id")
  MessageDto mapMessageToDto(Message message);

}

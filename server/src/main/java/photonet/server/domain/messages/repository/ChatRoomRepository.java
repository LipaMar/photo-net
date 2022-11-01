package photonet.server.domain.messages.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import photonet.server.domain.entity.User;
import photonet.server.domain.messages.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  @Query("select c from ChatRoom c where c.recipient.userName = :userName or c.sender.userName = :userName")
  List<ChatRoom> findAllChatRoomsForUserId(String userName);

  @Query("select c from ChatRoom c where (c.sender = :user and c.recipient = :user2) or (c.sender = :user2 and c.recipient = :user)")
  Optional<ChatRoom> findBySenderAndRecipient(User user, User user2);

}

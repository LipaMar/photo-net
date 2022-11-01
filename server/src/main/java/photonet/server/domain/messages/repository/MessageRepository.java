package photonet.server.domain.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.messages.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}

package photonet.server.domain.messages.entity;

import lombok.*;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages;

}

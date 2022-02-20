package photonet.server.domain.messages.entity;

import lombok.*;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private User author;

}

package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import photonet.server.domain.entity.Opinion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "comment")
@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Comment extends Opinion {

    @Column(columnDefinition="TEXT")
    private String content;
    private Date added;
    private boolean anonymous;

}

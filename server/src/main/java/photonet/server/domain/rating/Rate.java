package photonet.server.domain.rating;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import photonet.server.domain.entity.Opinion;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "rate")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Rate extends Opinion {

    private Integer rating;

}

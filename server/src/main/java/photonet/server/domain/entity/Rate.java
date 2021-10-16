package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
